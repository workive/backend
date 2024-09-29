package app.teamwize.api.leavepolicy.model.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.leavepolicy.model.LeaveTypeCycle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "leave_types")
@NoArgsConstructor
public class LeaveType extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "leave_type_id_seq_generator")
    @SequenceGenerator(name = "leave_type_id_seq_generator", sequenceName = "leave_type_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private LeaveTypeCycle cycle;
    private Integer amount;
    private boolean requiresApproval;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private LeavePolicy policy;

    public LeaveType(Long organizationId) {
        this.id = organizationId;
    }
}