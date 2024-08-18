package app.teamwize.api.dayoff.domain.request;

import app.teamwize.api.dayoff.domain.DayOffType;

import java.time.LocalDateTime;

public record DayOffCreateRequest(
        DayOffType type,
        String reason,
        LocalDateTime start,
        LocalDateTime end
) {
}
