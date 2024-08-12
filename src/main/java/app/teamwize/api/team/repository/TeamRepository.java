package app.teamwize.api.team.repository;

import app.teamwize.api.team.domain.TeamStatus;
import app.teamwize.api.team.domain.entity.Team;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends BaseJpaRepository<Team, Long> {


    List<Team> findByOrganizationId(Long organizationId);

    Optional<Team> findByOrganizationIdAndId(Long organizationId, Long id);

    List<Team> findByOrganizationIdAndStatusIsIn(Long organizationId, List<TeamStatus> statuses, PageRequest page);
}
