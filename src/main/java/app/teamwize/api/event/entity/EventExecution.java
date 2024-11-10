package app.teamwize.api.event.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.event.model.EventExecutionStatus;
import app.teamwize.api.event.model.EventExitCode;
import app.teamwize.api.event.model.EventStatus;
import app.teamwize.api.event.model.EventType;
import app.teamwize.api.organization.domain.entity.Organization;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
@Entity
@Table(name = "event_executions")
public class EventExecution extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "event_execution_id_seq_gen")
    @SequenceGenerator(name = "event_execution_id_seq_gen", sequenceName = "event_execution_id_seq", allocationSize = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    private EventExecutionStatus status;

    @Enumerated(EnumType.STRING)
    private EventExitCode exitCode;

    private Integer attempts;

    private String handler;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;
}
