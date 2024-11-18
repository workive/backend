package app.teamwize.api.event.repository;

import app.teamwize.api.event.entity.EventExecutionEntity;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventExecutionRepository extends BaseJpaRepository<EventExecutionEntity, Long>, JpaSpecificationExecutor<EventExecutionEntity> {
    Page<EventExecutionEntity> findByEventId(Long eventId,Pageable pageable);
}
