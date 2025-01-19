package app.teamwize.api.holiday.repository;

import app.teamwize.api.holiday.domain.HolidaysOverview;
import app.teamwize.api.holiday.domain.entity.Holiday;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequestMapping
public interface HolidayRepository extends BaseJpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
    List<Holiday> findByOrganizationIdAndCountryAndDateIsBetween(Long organizationId, String country, LocalDate startDate, LocalDate endDate);

    @Query("""
                    SELECT new app.teamwize.api.holiday.domain.HolidaysOverview(
                           h.country,
                           YEAR(h.date),
                           COUNT(h)
                       )
                       FROM Holiday h
                       WHERE h.organization.id = :organizationId
                       GROUP BY h.country, YEAR(h.date)
            """)
    List<HolidaysOverview> findByOverview(Long organizationId);
}
