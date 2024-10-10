package app.teamwize.api.leave.model.command;

import java.time.LocalDateTime;

public record LeaveCreateCommand(
        Long activatedTypeId,
        String reason,
        LocalDateTime start,
        LocalDateTime end) {
}
