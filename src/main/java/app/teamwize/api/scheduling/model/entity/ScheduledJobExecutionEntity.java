package app.teamwize.api.scheduling.model.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.scheduling.model.ExecutionStatus;
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
@Table(name = "scheduled_job_executions")
@NoArgsConstructor
public class ScheduledJobExecutionEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(generator = "scheduled_job_execution_id_seq_generator")
    @SequenceGenerator(name = "scheduled_job_execution_id_seq_generator", sequenceName = "scheduled_job_execution_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduled_job_id", nullable = false)
    private ScheduledJobEntity scheduledJob;

    private Instant executedAt;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;


    @Type(JsonType.class)
    private Map<String, Object> metadata;

    private String errorMessage;
}
