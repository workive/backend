package app.teamwize.api.leave.domain.request;

import app.teamwize.api.leave.domain.LeaveStatus;

public record LeaveUpdateRequest(LeaveStatus status) {
}
