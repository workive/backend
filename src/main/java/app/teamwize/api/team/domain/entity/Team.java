package app.teamwize.api.team.domain.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.team.domain.TeamStatus;
import app.teamwize.api.organization.domain.entity.Organization;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "teams")
@NoArgsConstructor
public class Team extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "team_id_seq_generator")
    @SequenceGenerator(name = "team_id_seq_generator", sequenceName = "team_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    @ManyToOne
    private Organization organization;

    @Type(JsonType.class)
    private Map<String,Object> metadata;

    public Team(Long organizationId) {
        this.id = organizationId;
    }
}
