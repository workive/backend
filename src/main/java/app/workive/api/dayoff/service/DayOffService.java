package app.workive.api.dayoff.service;

import app.workive.api.base.domain.model.request.PaginationRequest;
import app.workive.api.dayoff.domain.DayOffStatus;
import app.workive.api.dayoff.domain.entity.DayOff;
import app.workive.api.dayoff.domain.request.DayOffCreateRequest;
import app.workive.api.dayoff.domain.request.DayOffFilterRequest;
import app.workive.api.dayoff.domain.request.DayOffUpdateRequest;
import app.workive.api.dayoff.exception.DayOffNotFoundException;
import app.workive.api.dayoff.exception.DayOffUpdateStatusFailedException;
import app.workive.api.dayoff.repository.DayOffRepository;
import app.workive.api.organization.domain.entity.Organization;
import app.workive.api.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static app.workive.api.dayoff.repository.DayOffSpecifications.*;

@Service
@RequiredArgsConstructor
public class DayOffService {

    private final DayOffRepository dayOffRepository;

    @Transactional
    public DayOff createDayOff(Long organizationId, Long userId, DayOffCreateRequest request) {
        var dayOff = new DayOff()
                .setStartAt(request.start())
                .setEndAt(request.end())
                .setUser(new User(userId))
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
