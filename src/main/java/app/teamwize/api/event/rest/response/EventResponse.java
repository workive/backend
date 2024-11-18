package app.teamwize.api.event.rest.response;

import app.teamwize.api.event.model.EventStatus;
import app.teamwize.api.event.model.EventType;

import java.time.Instant;
import java.util.Map;

public record EventResponse(
        Long id,
        EventType type,
        Map<String, Object> params,
        EventStatus status,
        Byte maxAttempts,
        Instant scheduledAt
) {
}
