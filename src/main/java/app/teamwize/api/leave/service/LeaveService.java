package app.teamwize.api.leave.service;

import app.teamwize.api.auth.domain.event.UserEventPayload;
import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.event.service.EventService;
import app.teamwize.api.holiday.domain.entity.Holiday;
import app.teamwize.api.holiday.service.HolidayService;
import app.teamwize.api.leave.model.LeaveStatus;
import app.teamwize.api.leave.model.command.LeaveCreateCommand;
import app.teamwize.api.leave.model.command.LeaveUpdateCommand;
import app.teamwize.api.leave.model.entity.Leave;
import app.teamwize.api.leave.model.event.LeaveCreatedEvent;
import app.teamwize.api.leave.model.event.LeaveEventPayload;
import app.teamwize.api.leave.model.event.LeaveStatusUpdatedEvent;
import app.teamwize.api.leave.rest.mapper.LeaveMapper;
import app.teamwize.api.leave.rest.model.request.LeaveFilterRequest;
import app.teamwize.api.leave.exception.LeaveNotFoundException;
import app.teamwize.api.leave.exception.LeaveUpdateStatusFailedException;
import app.teamwize.api.leave.repository.LeaveRepository;
import app.teamwize.api.leave.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leave.model.UserLeaveBalance;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.mapper.OrganizationMapper;
import app.teamwize.api.organization.service.OrganizationService;
import app.teamwize.api.user.domain.entity.User;
import app.teamwize.api.user.exception.UserNotFoundException;
import app.teamwize.api.user.mapper.UserMapper;
import app.teamwize.api.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static app.teamwize.api.leave.repository.LeaveSpecifications.*;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final UserService userService;
    private final HolidayService holidayService;
    private final OrganizationService organizationService;
    private final LeavePolicyService leavePolicyService;
    private final EventService eventService;

    @Transactional
    public Leave createLeave(Long organizationId, Long userId, LeaveCreateCommand command) throws UserNotFoundException, LeaveTypeNotFoundException, OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);
        var user = userService.getUser(organizationId, userId);
        var leavePolicy = leavePolicyService.getLeavePolicy(organizationId, user.getLeavePolicy().getId());

        var leaveType = leavePolicy.getActivatedTypes().stream()
                .filter(type -> type.getId().equals(command.activatedTypeId()))
                .findFirst()
                .orElseThrow(() -> new LeaveTypeNotFoundException(command.activatedTypeId()));

        var dayOff = new Leave()
                .setReason(command.reason())
                .setStartAt(command.start())
                .setEndAt(command.end())
                .setUser(user)
                .setOrganization(organization)
                .setStatus(LeaveStatus.PENDING)
                .setType(leaveType)
                .setDuration(calculateLeaveDuration(organization, user, command.start(), command.end()));
        dayOff = leaveRepository.persist(dayOff);


       // eventService.emmit(organizationId, new LeaveCreatedEvent(new LeaveEventPayload(dayOff), new UserEventPayload(user)));

        return dayOff;
    }

    public Page<Leave> getLeaves(Long organizationId, LeaveFilterRequest filters, PaginationRequest pagination) {
        var sort = Sort.by("id").descending();
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);

        var specs = hasOrganizationId(organizationId);


        if (filters.teamId() != null) {
            specs = specs.and(hasTeamId(filters.teamId()));
        }
        if (filters.userId() != null) {
            specs = specs.and(hasUserId(filters.userId()));
        }
        if (filters.status() != null) {
            specs = specs.and(hasStatus(filters.status()));
        }
        return leaveRepository.findAll(specs, pageRequest);

    }

    public Page<Leave> getLeaves(Long organizationId, Long userId, PaginationRequest pagination) {
        var paging = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), Sort.by("id").descending());
        return leaveRepository.findByOrganizationIdAndUserId(organizationId, userId, paging);
    }


    @Transactional
    public Leave updateLeave(Long organizationId, Long userId, Long id, LeaveUpdateCommand request) throws LeaveNotFoundException, LeaveUpdateStatusFailedException, UserNotFoundException {
        var user = userService.getUser(organizationId, userId);
        var leave = getById(userId, id);
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new LeaveUpdateStatusFailedException(id, leave.getStatus());
        }
        leave.setStatus(request.status());
        eventService.emmit(organizationId, new LeaveStatusUpdatedEvent(new LeaveEventPayload(leave), new UserEventPayload(user)));
        return leaveRepository.update(leave);
    }

    public Leave getLeave(Long userId, Long id) throws LeaveNotFoundException {
        return getById(userId, id);
    }

    public Float getTotalDuration(Long organizationId, Long userId, Long typeId, LeaveStatus status) {
        var sum = leaveRepository.countByOrganizationIdAndUserIdAndTypeId(organizationId, userId, typeId, status);
        return sum != null ? sum : 0f;
    }

    private Leave getById(Long userId, Long id) throws LeaveNotFoundException {
        return leaveRepository.findByUserIdAndId(userId, id).orElseThrow(() -> new LeaveNotFoundException(id));
    }

    public List<UserLeaveBalance> getLeaveBalance(Long organizationId, Long userId) throws UserNotFoundException, LeaveTypeNotFoundException {
        var user = userService.getUser(organizationId, userId);
        var policy = leavePolicyService.getLeavePolicy(organizationId, user.getLeavePolicy().getId());
        var startedAt = user.getCreatedAt().toLocalDate();
        var result = new ArrayList<UserLeaveBalance>();
        for (var activatedType : policy.getActivatedTypes()) {
            var usedAmount = this.getTotalDuration(organizationId, userId, activatedType.getId(), LeaveStatus.ACCEPTED);
            var totalAmount = switch (activatedType.getType().getCycle()) {
                case UNLIMITED -> Integer.MAX_VALUE;
                case PER_MONTH ->
                        Period.between(startedAt, LocalDate.now()).toTotalMonths() * activatedType.getAmount();
                case PER_YEAR ->
                        (Period.between(startedAt, LocalDate.now()).toTotalMonths() / 12) * activatedType.getAmount();
            };
            result.add(new UserLeaveBalance(activatedType, usedAmount.longValue(), totalAmount, startedAt));
        }
        return result;
    }

    private Float calculateLeaveDuration(Organization organization, User user, LocalDateTime start, LocalDateTime end) {
        var startDate = start.toLocalDate();
        var endDate = end.toLocalDate();

        var holidayDates = holidayService.getHolidays(organization.getId(), startDate, endDate, user.getCountry())
                .stream()
                .map(Holiday::getDate)
                .collect(Collectors.toSet());

        var workingDays = Arrays.stream(organization.getWorkingDays()).collect(Collectors.toSet());

        var leaveDays = startDate.datesUntil(endDate)
                .filter(date -> !holidayDates.contains(date))
                .filter(date -> workingDays.contains(date.getDayOfWeek()))
                .count();

        return (float) leaveDays;

    }


}
