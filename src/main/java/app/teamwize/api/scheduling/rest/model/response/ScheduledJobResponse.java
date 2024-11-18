package app.teamwize.api.scheduling.rest.model.response;

import app.teamwize.api.base.domain.entity.EntityStatus;

import java.time.Instant;
import java.util.Map;

public record ScheduledJobResponse(
        Long id,
        String name,
        String cronExpression,
        String runner,
        EntityStatus status,
        Instant lastExecutedAt,
        Instant nextExecutionAt,
        Map<String, Object> metadata) {
}
