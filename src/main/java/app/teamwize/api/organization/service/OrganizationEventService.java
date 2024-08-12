package app.teamwize.api.organization.service;


import app.teamwize.api.user.domain.response.UserResponse;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.organization.domain.event.OrganizationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationEventService {

    private final ApplicationEventPublisher publisher;

    public void sendOrganizationCreatedEvent(Organization organization, UserResponse admin) {
        var event = new OrganizationCreatedEvent(organization, admin);
        publisher.publishEvent(event);
    }
}
