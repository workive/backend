package app.workive.api.team.service;

import app.workive.api.organization.exception.OrganizationNotFoundException;
import app.workive.api.organization.service.OrganizationService;
import app.workive.api.team.domain.TeamStatus;
import app.workive.api.team.domain.entity.Team;
import app.workive.api.team.domain.exception.TeamNotFoundException;
import app.workive.api.team.domain.request.TeamCreateRequest;
import app.workive.api.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final OrganizationService organizationService;

    public List<Team> getTeams(Long organizationId) {
        return teamRepository.findByOrganizationIdAndStatusIsIn(organizationId,List.of(TeamStatus.DEFAULT));
    }

    @Transactional
    public Team createTeam(Long organizationId, TeamCreateRequest request) throws OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);
        var team = new Team();
        team.setName(request.name())
                .setMetadata(request.metadata())
                .setStatus(TeamStatus.DEFAULT)
                .setOrganization(organization);
        return teamRepository.persist(team);
    }

    @Transactional
    public Team updateTeam(Long organizationId, Long teamId, TeamCreateRequest request) throws TeamNotFoundException {
        var team = getTeam(organizationId, teamId)
                .setName(request.name())
                .setMetadata(request.metadata());
        return teamRepository.merge(team);
    }


    public Team getTeam(Long organizationId, Long teamId) throws TeamNotFoundException {
        return teamRepository.findByOrganizationIdAndId(organizationId, teamId)
                .orElseThrow(() -> new TeamNotFoundException(organizationId, teamId));
    }

    @Transactional
    public void removeTeam(Long organizationId,Long teamId) throws TeamNotFoundException {
        var team = getTeam(organizationId,teamId);
        team.setStatus(TeamStatus.ARCHIVED);
        teamRepository.update(team);
    }
}
