package app.teamwize.api.leave.service;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.holiday.domain.entity.Holiday;
import app.teamwize.api.holiday.service.HolidayService;
import app.teamwize.api.leave.domain.LeaveStatus;
import app.teamwize.api.leave.domain.entity.Leave;
import app.teamwize.api.leave.domain.request.LeaveCreateRequest;
import app.teamwize.api.leave.domain.request.LeaveFilterRequest;
import app.teamwize.api.leave.domain.request.LeaveUpdateRequest;
import app.teamwize.api.leave.exception.LeaveNotFoundException;
import app.teamwize.api.leave.exception.LeaveUpdateStatusFailedException;
import app.teamwize.api.leave.repository.LeaveRepository;
import app.teamwize.api.leavepolicy.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leavepolicy.model.UserLeaveBalance;
import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import app.teamwize.api.leavepolicy.service.LeavePolicyService;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.service.OrganizationService;
import app.teamwize.api.user.domain.entity.User;
import app.teamwize.api.user.exception.UserNotFoundException;
import app.teamwize.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Transactional
    public Leave createLeave(Long organizationId, Long userId, LeaveCreateRequest request) throws UserNotFoundException, LeaveTypeNotFoundException, OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);
        var user = userService.getUser(organizationId, userId);
        var leavePolicy = leavePolicyService.getLeavePolicy(organizationId, user.getLeavePolicy().getId());

        var leaveType = leavePolicy.getTypes().stream()
                .filter(type -> type.getId().equals(request.typeId()))
                .findFirst()
                .orElseThrow(() -> new LeaveTypeNotFoundException(request.typeId()));

        var dayOff = new Leave()
                .setReason(request.reason())
                .setStartAt(request.start())
                .setEndAt(request.end())
                .setUser(user)
                .setOrganization(new Organization(organizationId))
                .setStatus(LeaveStatus.PENDING)
                .setType(leaveType)
                .setDuration(calculateLeaveDuration(organization, leaveType, user, request.start(), request.end()));
        return leaveRepository.persist(dayOff);
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
    public Leave updateLeave(Long userId, Long id, LeaveUpdateRequest request) throws LeaveNotFoundException, LeaveUpdateStatusFailedException {
        var dayOff = getById(userId, id);
        if (dayOff.getStatus() != LeaveStatus.PENDING) {
            throw new LeaveUpdateStatusFailedException(id, dayOff.getStatus());
        }
        dayOff.setStatus(request.status());
        return leaveRepository.update(dayOff);
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
        for (var type : policy.getTypes()) {
            var usedAmount = this.getTotalDuration(organizationId, userId, type.getId(), LeaveStatus.ACCEPTED);
            var totalAmount = switch (type.getCycle()) {
                case UNLIMITED -> Integer.MAX_VALUE;
                case PER_MONTH -> Period.between(startedAt, LocalDate.now()).toTotalMonths() * type.getAmount();
                case PER_YEAR -> (Period.between(startedAt, LocalDate.now()).toTotalMonths() / 12) * type.getAmount();
            };
            result.add(new UserLeaveBalance(type, usedAmount.longValue(), totalAmount, startedAt));
        }
        return result;
    }

    private Float calculateLeaveDuration(Organization organization, LeaveType type, User user, LocalDateTime start, LocalDateTime end) {
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
