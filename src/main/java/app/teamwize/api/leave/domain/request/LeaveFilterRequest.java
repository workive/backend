package app.teamwize.api.leave.domain.request;

import app.teamwize.api.leave.domain.LeaveStatus;

public record LeaveFilterRequest(Long teamId, Long userId, LeaveStatus status) {
}
