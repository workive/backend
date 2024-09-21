package app.teamwize.api.assets.domain.entity;


import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.assets.domain.model.AssetCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "assets")
public class Asset extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "asset_id_seq_gen")
    @SequenceGenerator(name = "asset_id_seq_gen", sequenceName = "asset_id_seq", allocationSize = 1)
    private Long id;
    private Long size;
    private String path;
    private String name;
    private String originalName;
    private String url;
    private String contentType;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
