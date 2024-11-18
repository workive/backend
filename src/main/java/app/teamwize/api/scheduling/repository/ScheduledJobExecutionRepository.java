package app.teamwize.api.scheduling.repository;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.scheduling.model.ScheduledJobExecution;
import app.teamwize.api.scheduling.model.entity.ScheduledJobEntity;
import app.teamwize.api.scheduling.model.entity.ScheduledJobExecutionEntity;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.List;

public interface ScheduledJobExecutionRepository extends BaseJpaRepository<ScheduledJobExecutionEntity, Long>, JpaSpecificationExecutor<ScheduledJobExecutionEntity> {
    Page<ScheduledJobExecutionEntity> findByScheduledJobId(Long jobId, PageRequest pageRequest);
}
