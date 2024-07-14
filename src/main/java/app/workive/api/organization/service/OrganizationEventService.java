package app.workive.api.organization.service;


import app.workive.api.organization.domain.entity.Organization;
import app.workive.api.organization.domain.event.OrganizationCreatedEvent;
import app.workive.api.user.domain.response.UserResponse;
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
