package app.teamwize.api.auth.domain.event;

import app.teamwize.api.organization.domain.entity.Organization;

public record OrganizationEventPayload(Long id, String name) {
    public OrganizationEventPayload(Organization organization) {
        this(organization.getId(), organization.getName());
    }
}