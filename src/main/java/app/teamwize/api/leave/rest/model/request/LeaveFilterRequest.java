package app.teamwize.api.leave.rest.model.request;

import app.teamwize.api.leave.model.LeaveStatus;

public record LeaveFilterRequest(Long teamId, Long userId, LeaveStatus status) {
}
