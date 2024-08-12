package app.teamwize.api.organization.service;


import app.teamwize.api.base.exception.BaseException;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.organization.domain.event.OrganizationCreateRequest;
import app.teamwize.api.organization.domain.request.OrganizationUpdateRequest;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Organization getOrganization(long organizationId) throws OrganizationNotFoundException {
        return getById(organizationId);
    }

    @Transactional(rollbackFor = BaseException.class)
    public Organization registerOrganization(OrganizationCreateRequest request) {
        Organization organization = buildOrganization(request);
        return organizationRepository.persist(organization);
    }

    public Organization updateOrganization(long organizationId, OrganizationUpdateRequest request) throws OrganizationNotFoundException {
        var organization = getById(organizationId)
                .setName(request.name())
                .setTimezone(request.timezone())
                .setWeekFirstDay(request.weekFirstDay())
                .setWorkingDays(request.workingDays().toArray(DayOfWeek[]::new))
                .setMetadata(request.metadata())
                .setCountry(request.country());
        return organizationRepository.update(organization);
    }

    private Organization getById(Long organizationId) throws OrganizationNotFoundException {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId));
    }

    private Organization buildOrganization(OrganizationCreateRequest request) {
        return new Organization()
                .setName(request.name())
                .setCountry(request.country())
                .setTimezone(request.timezone())
                .setWeekFirstDay(DayOfWeek.MONDAY)
                .setWorkingDays(new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY});
    }


}
