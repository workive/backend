package app.teamwize.api.event.repository;

import app.teamwize.api.event.entity.Event;
import app.teamwize.api.event.entity.EventExecution;
import app.teamwize.api.event.model.EventStatus;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventExecutionRepository extends BaseJpaRepository<EventExecution, Long>, JpaSpecificationExecutor<EventExecution> {

}
