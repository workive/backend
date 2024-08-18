package app.teamwize.api.dayoff.domain.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.dayoff.domain.DayOffStatus;
import app.teamwize.api.dayoff.domain.DayOffType;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "days_off")
public class DayOff extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "day_off_id_seq_generator")
    @SequenceGenerator(name = "day_off_id_seq_generator", sequenceName = "day_off_id_seq", allocationSize = 1)
    protected Long id;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    private DayOffStatus status;

    @Enumerated(EnumType.STRING)
    private DayOffType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

}