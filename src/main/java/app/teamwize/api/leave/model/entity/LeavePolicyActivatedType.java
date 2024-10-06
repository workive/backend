package app.teamwize.api.leave.model.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.base.domain.entity.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "leave_policy_activated_types")
@NoArgsConstructor
public class LeavePolicyActivatedType extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "leave_policy_activated_type_id_seq_generator")
    @SequenceGenerator(name = "leave_policy_activated_type_id_seq_generator", sequenceName = "leave_policy_activated_type_id_seq", allocationSize = 5)
    private Long id;

    private Integer amount;
    private boolean requiresApproval;
    @ManyToOne
    private LeaveType type;
    @ManyToOne
    private LeavePolicy policy;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
}
