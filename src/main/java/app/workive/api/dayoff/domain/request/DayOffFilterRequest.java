package app.workive.api.dayoff.domain.request;

import app.workive.api.dayoff.domain.DayOffStatus;

public record DayOffFilterRequest(Long teamId, Long userId, DayOffStatus status) {
}
