package app.teamwize.api.leave.domain.request;


import java.time.LocalDateTime;

public record LeaveCreateRequest(
        Long typeId,
        String reason,
        LocalDateTime start,
        LocalDateTime end
) {
}
