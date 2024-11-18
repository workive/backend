package app.teamwize.api.leave.model.event;

import app.teamwize.api.auth.domain.event.UserEventPayload;
import app.teamwize.api.event.model.EventPayload;
import app.teamwize.api.event.model.EventType;

import java.util.Map;

public record LeaveCreatedEvent(LeaveEventPayload leave, UserEventPayload user) implements EventPayload {

    @Override
    public EventType name() {
        return EventType.LEAVE_CREATED;
    }

    @Override
    public Map<String, Object> payload() {
        return Map.of(
                "leave", leave,
                "user", user
        );
    }


}
