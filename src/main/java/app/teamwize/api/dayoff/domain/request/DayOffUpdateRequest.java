package app.teamwize.api.dayoff.domain.request;

import app.teamwize.api.dayoff.domain.DayOffStatus;

public record DayOffUpdateRequest(DayOffStatus status) {
}
