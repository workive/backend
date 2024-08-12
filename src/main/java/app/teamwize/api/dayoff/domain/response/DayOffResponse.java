package app.teamwize.api.dayoff.domain.response;

import app.teamwize.api.dayoff.domain.DayOffStatus;
import app.teamwize.api.dayoff.domain.DayOffType;
import app.teamwize.api.user.domain.response.UserCompactResponse;

import java.time.LocalDateTime;

public record DayOffResponse(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime startAt,
        LocalDateTime endAt,
        DayOffStatus status,
        DayOffType type,
        UserCompactResponse user) {
}
