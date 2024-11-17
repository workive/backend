package app.teamwize.api.event.model;


import java.time.LocalDateTime;
import java.util.Map;


public record EventExecution(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        EventExecutionStatus status,
        EventExitCode exitCode,
        Integer attempts,
        String handler,
        Map<String, Object> metadata) {
}
