package app.teamwize.api.dayoff.domain.request;

import app.teamwize.api.dayoff.domain.DayOffType;

import java.time.LocalDateTime;

public record DayOffCreateRequest(
        DayOffType type,
        LocalDateTime start,
        LocalDateTime end
) {
}
