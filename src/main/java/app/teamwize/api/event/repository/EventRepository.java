package app.teamwize.api.event.repository;

import app.teamwize.api.event.entity.Event;
import app.teamwize.api.event.model.EventExitCode;
import app.teamwize.api.event.model.EventStatus;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EventRepository extends BaseJpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @EntityGraph(attributePaths = {"executions"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("from Event e where e.status=:status and e.scheduledAt < CURRENT_TIMESTAMP  order by e.scheduledAt")
    List<Event> findByStatus(EventStatus status);

    @Modifying
    @Query(value = "update Event e set e.status=:status where e.organization.id=:organizationId and e.id=:id")
    void updateStatus(Long organizationId, Long id, EventStatus status);

}
