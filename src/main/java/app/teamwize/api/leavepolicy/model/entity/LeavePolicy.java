package app.teamwize.api.leavepolicy.model.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.organization.domain.entity.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "leave_policies")
@NoArgsConstructor
public class LeavePolicy extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "leave_policy_id_seq_generator")
    @SequenceGenerator(name = "leave_policy_id_seq_generator", sequenceName = "leave_policy_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private boolean isDefault;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    @OneToMany(
            mappedBy = "policy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LeaveType> types;
}
