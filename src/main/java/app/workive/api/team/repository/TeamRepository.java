package app.workive.api.team.repository;

import app.workive.api.team.domain.TeamStatus;
import app.workive.api.team.domain.entity.Team;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends BaseJpaRepository<Team, Long> {


    List<Team> findByOrganizationId(Long organizationId);

    Optional<Team> findByOrganizationIdAndId(Long organizationId, Long id);

    List<Team> findByOrganizationIdAndStatusIsIn(Long organizationId, List<TeamStatus> statuses);
}
