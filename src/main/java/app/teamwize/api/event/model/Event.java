package app.teamwize.api.event.model;

import java.time.Instant;
import java.util.Map;

public record Event(
        Long id,
        EventType type,
        Map<String, Object> params,
        EventStatus status,
        Byte maxAttempts,
        Instant scheduledAt
) {
}
