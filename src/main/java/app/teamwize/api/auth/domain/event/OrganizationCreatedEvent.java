package app.teamwize.api.auth.domain.event;

import app.teamwize.api.event.model.EventPayload;
import app.teamwize.api.event.model.EventType;

import java.util.Map;

public record OrganizationCreatedEvent(UserEventPayload user,
                                       OrganizationEventPayload organization) implements EventPayload {
    @Override
    public EventType name() {
        return EventType.ORGANIZATION_CREATED;
    }

    @Override
    public Map<String, Object> payload() {
        return Map.of(
                "organization", organization,
                "user", user
        );
    }



}