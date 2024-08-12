package app.teamwize.api.organization.domain.event;

import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.domain.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationCreatedEvent {
    private Organization organization;
    private UserResponse user;
}
