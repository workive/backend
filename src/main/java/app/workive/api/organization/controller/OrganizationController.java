package app.workive.api.organization.controller;


import app.workive.api.auth.service.SecurityService;
import app.workive.api.organization.domain.request.OrganizationUpdateRequest;
import app.workive.api.organization.domain.response.OrganizationResponse;
import app.workive.api.organization.exception.OrganizationNotFoundException;
import app.workive.api.organization.mapper.OrganizationMapper;
import app.workive.api.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final SecurityService securityService;
    private final OrganizationMapper organizationMapper;


    @GetMapping("default")
    public OrganizationResponse getOrganization() throws OrganizationNotFoundException {
        return organizationMapper.toResponse(organizationService.getOrganization(securityService.getUserOrganizationId()));
    }

    @PutMapping("default")
    @PreAuthorize("hasAnyRole('admin')")
    public OrganizationResponse updateOrganization(@Valid @RequestBody OrganizationUpdateRequest request)
            throws OrganizationNotFoundException {
        return organizationMapper.toResponse(organizationService.updateOrganization(securityService.getUserOrganizationId(), request));
    }
}
