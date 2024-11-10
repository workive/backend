package app.teamwize.api.scheduling.rest.model.response;


import app.teamwize.api.scheduling.model.ExecutionStatus;

import java.time.Instant;
import java.util.Map;

public record ScheduledJobExecutionResponse(
        Long id,
        Instant executedAt,
        ExecutionStatus status,
        Map<String, Object> metadata,
        String errorMessage) {
}
