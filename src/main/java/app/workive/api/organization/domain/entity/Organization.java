package app.workive.api.organization.domain.entity;

import app.workive.api.base.domain.entity.BaseAuditEntity;
import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.hibernate.type.SqlTypes;

import java.time.DayOfWeek;
import java.util.Map;


@Getter
@Setter
@Entity
@Table(name = "organizations")
@NoArgsConstructor
public class Organization extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "organization_id_seq_generator")
    @SequenceGenerator(name = "organization_id_seq_generator", sequenceName = "organization_id_seq", allocationSize = 1)
    private Long id;
    private String name;

    private String country;

    private String timezone;

    @Type(value = EnumArrayType.class, parameters = @Parameter(name = AbstractArrayType.SQL_ARRAY_TYPE, value = "weekday"))
    @Column(columnDefinition = "weekday[]")
    private DayOfWeek[] workingDays;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "weekday")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private DayOfWeek weekFirstDay;

    @Type(JsonType.class)
    private Map<String, Object> metadata;

    public Organization(Long organizationId) {
        this.id = organizationId;
    }
}