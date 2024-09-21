package app.teamwize.api.holiday.domain.entity;

import app.teamwize.api.base.domain.entity.BaseAuditEntity;
import app.teamwize.api.holiday.domain.HolidayStatus;
import app.teamwize.api.organization.domain.entity.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "holidays")
public class Holiday extends BaseAuditEntity {
    @Id
    @GeneratedValue(generator = "holiday_id_seq_generator")
    @SequenceGenerator(name = "holiday_id_seq_generator", sequenceName = "holiday_id_seq", allocationSize = 10)
    protected Long id;

    private LocalDate date;

    private String country;

    private String description;

    @Enumerated(EnumType.STRING)
    private HolidayStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

}