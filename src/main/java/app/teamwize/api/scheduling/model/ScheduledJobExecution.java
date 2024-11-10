package app.teamwize.api.scheduling.model;


import java.time.Instant;
import java.util.Map;

public record ScheduledJobExecution(
        Long id,
        Instant executedAt,
        ExecutionStatus status,
        Map<String, Object> metadata,
        String errorMessage) {
}
