package app.teamwize.api.dayoff.domain.request;

import app.teamwize.api.dayoff.domain.DayOffStatus;

public record DayOffFilterRequest(Long teamId, Long userId, DayOffStatus status) {
}
