package app.teamwize.api.scheduling.repository;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.scheduling.model.entity.ScheduledJobEntity;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.List;

public interface ScheduledJobRepository extends BaseJpaRepository<ScheduledJobEntity, Long>, JpaSpecificationExecutor<ScheduledJobEntity> {

    List<ScheduledJobEntity> findByStatusAndNextExecutionAtBefore(EntityStatus status, Instant currentTime);

    List<ScheduledJobEntity> findAll();

}
