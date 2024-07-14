package app.workive.api.team.repository;

import app.workive.api.team.domain.entity.Team;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;

import java.util.List;


public interface TeamRepository extends BaseJpaRepository<Team, Long> {


    List<Team> findByOrganizationId(Long organizationId);
}
