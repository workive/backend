package app.teamwize.api.scheduling.model;

import app.teamwize.api.base.domain.entity.EntityStatus;

import java.time.Instant;
import java.util.Map;

public record ScheduledJob(
        Long id,
        String name,
        String cronExpression,
        String runner,
        EntityStatus status,
        Instant lastExecutedAt,
        Instant nextExecutionAt,
        Map<String, Object> metadata
) {
}
