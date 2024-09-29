package app.teamwize.api.leave.domain.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.leave.domain.LeaveStatus;
import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "leaves")
public class Leave extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "day_off_id_seq_generator")
    @SequenceGenerator(name = "day_off_id_seq_generator", sequenceName = "day_off_id_seq", allocationSize = 1)
    protected Long id;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private LeaveType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String reason;

    private Float duration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

}