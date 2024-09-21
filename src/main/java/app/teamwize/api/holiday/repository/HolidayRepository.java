package app.teamwize.api.holiday.repository;

import app.teamwize.api.holiday.domain.entity.Holiday;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequestMapping
public interface HolidayRepository extends BaseJpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
    List<Holiday> findByOrganizationIdAndCountryAndDateIsBetween(Long organizationId, String country, LocalDate startDate, LocalDate endDate);
}
