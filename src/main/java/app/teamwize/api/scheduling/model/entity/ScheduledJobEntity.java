package app.teamwize.api.scheduling.model.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.base.domain.entity.EntityStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "scheduled_jobs")
@NoArgsConstructor
public class ScheduledJobEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(generator = "scheduled_job_id_seq_generator")
    @SequenceGenerator(name = "scheduled_job_id_seq_generator", sequenceName = "scheduled_job_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String cronExpression;

    private String runner;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    private Instant lastExecutedAt;

    private Instant nextExecutionAt;

    @Type(JsonType.class)
    private Map<String, Object> metadata;
}