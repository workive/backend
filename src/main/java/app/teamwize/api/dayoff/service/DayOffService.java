package app.teamwize.api.dayoff.service;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.dayoff.domain.request.DayOffCreateRequest;
import app.teamwize.api.dayoff.domain.DayOffStatus;
import app.teamwize.api.dayoff.domain.entity.DayOff;
import app.teamwize.api.dayoff.domain.request.DayOffFilterRequest;
import app.teamwize.api.dayoff.domain.request.DayOffUpdateRequest;
import app.teamwize.api.dayoff.exception.DayOffNotFoundException;
import app.teamwize.api.dayoff.exception.DayOffUpdateStatusFailedException;
import app.teamwize.api.dayoff.repository.DayOffRepository;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.exception.UserNotFoundException;
import app.teamwize.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static app.teamwize.api.dayoff.repository.DayOffSpecifications.*;

@Service
@RequiredArgsConstructor
public class DayOffService {

    private final DayOffRepository dayOffRepository;
    private final UserService userService;

    @Transactional
    public DayOff createDayOff(Long organizationId, Long userId, DayOffCreateRequest request) throws UserNotFoundException {
        var user = userService.getUser(organizationId,userId);
        var dayOff = new DayOff()
                .setReason(request.reason())
                .setStartAt(request.start())
                .setEndAt(request.end())
                .setUser(user)
                .setOrganization(new Organization(organizationId))
                .setStatus(DayOffStatus.PENDING)
                .setType(request.type());
        return dayOffRepository.persist(dayOff);
    }

    public Page<DayOff> getDaysOff(Long organizationId, DayOffFilterRequest filters, PaginationRequest pagination) {
        var sort = Sort.by("id").descending();
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);

        var specs = hasOrganizationId(organizationId);


        if (filters.teamId() != null) {
            specs = specs.and(hasTeamId(filters.teamId()));
        }
        if(filters.userId() != null) {
            specs = specs.and(hasUserId(filters.userId()));
        }
        if(filters.status() != null) {
            specs = specs.and(hasStatus(filters.status()));
        }
        return dayOffRepository.findAll(specs, pageRequest);

    }

    public Page<DayOff> getDaysOff(Long organizationId, Long userId, PaginationRequest pagination) {
        var paging = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), Sort.by("id").descending());
        return dayOffRepository.findByOrganizationIdAndUserId(organizationId, userId, paging);
    }


    @Transactional
    public DayOff updateDayOff(Long userId, Long id, DayOffUpdateRequest request) throws DayOffNotFoundException, DayOffUpdateStatusFailedException {
        var dayOff = getById(userId, id);
        if (dayOff.getStatus() != DayOffStatus.PENDING) {
            throw new DayOffUpdateStatusFailedException(id, dayOff.getStatus());
        }
        dayOff.setStatus(request.status());
        return dayOffRepository.update(dayOff);
    }

    public DayOff getDayOff(Long userId, Long id) throws DayOffNotFoundException {
        return getById(userId, id);
    }

    private DayOff getById(Long userId, Long id) throws DayOffNotFoundException {
        return dayOffRepository.findByUserIdAndId(userId, id).orElseThrow(() -> new DayOffNotFoundException(id));
    }


}
