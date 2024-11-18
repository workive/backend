package app.teamwize.api.event.repository;

import app.teamwize.api.event.entity.EventEntity;
import app.teamwize.api.event.model.EventStatus;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends BaseJpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {
    @EntityGraph(attributePaths = {"executions"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("from EventEntity e where e.status=:status and e.scheduledAt < CURRENT_TIMESTAMP  order by e.scheduledAt")
    List<EventEntity> findByStatus(EventStatus status);

    @Modifying
    @Query(value = "update EventEntity e set e.status=:status where e.organization.id=:organizationId and e.id=:id")
    void updateStatus(Long organizationId, Long id, EventStatus status);

    Page<EventEntity> findAll(Pageable pageable);

    Optional<EventEntity> findByOrganizationIdAndId(Long organizationId, Long id);
}
