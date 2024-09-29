package app.teamwize.api.leave.domain.response;

import app.teamwize.api.leave.domain.LeaveStatus;
import app.teamwize.api.leavepolicy.rest.model.response.LeaveTypeResponse;
import app.teamwize.api.user.domain.response.UserCompactResponse;

import java.time.LocalDateTime;

public record LeaveResponse(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime startAt,
        LocalDateTime endAt,
        LeaveStatus status,
        Float duration,
        LeaveTypeResponse type,
        String reason,
        UserCompactResponse user) {
}
