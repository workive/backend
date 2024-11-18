package app.teamwize.api.team.domain.event;

import app.teamwize.api.event.model.EventPayload;
import app.teamwize.api.event.model.EventType;

import java.util.Map;

public record TeamCreatedEvent(TeamEventPayload team) implements EventPayload {
    @Override
    public EventType name() {
        return EventType.TEAM_CREATED;
    }

    @Override
    public Map<String, Object> payload() {
        return Map.of();
    }
}
