package app.teamwize.api.organization.controller;


import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.organization.domain.request.OrganizationUpdateRequest;
import app.teamwize.api.organization.domain.response.OrganizationResponse;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.mapper.OrganizationMapper;
import app.teamwize.api.organization.service.OrganizationService;
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
