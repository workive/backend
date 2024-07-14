package app.workive.api.dayoff.repository;

import app.workive.api.dayoff.domain.entity.DayOff;
import app.workive.api.user.domain.entity.User;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayOffRepository extends BaseJpaRepository<DayOff, Long>, JpaSpecificationExecutor<DayOff> {

    Optional<DayOff> findByUserIdAndId(Long userId, Long id);

    Page<DayOff> findByOrganizationId(Long organizationId, Pageable page);

    Page<DayOff> findByOrganizationIdAndUserId(Long organizationId, Long userId, Pageable page);


    @EntityGraph(attributePaths = {"user","user.team"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<DayOff> findAll(Specification<DayOff> spec, Pageable pageable);
}
