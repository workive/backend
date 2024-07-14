package app.workive.api.team.service;

import app.workive.api.organization.exception.OrganizationNotFoundException;
import app.workive.api.organization.repository.OrganizationRepository;
import app.workive.api.organization.service.OrganizationService;
import app.workive.api.team.domain.entity.Team;
import app.workive.api.team.domain.request.TeamCreateRequest;
import app.workive.api.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final OrganizationService organizationService;

    public List<Team> getTeams(Long organizationId) {
        return teamRepository.findByOrganizationId(organizationId);
    }

    public Team createTeam(Long organizationId,TeamCreateRequest request) throws OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);
        var team = new Team();
        team.setName(request.name())
                .setMetadata(request.metadata())
                .setOrganization(null);
        return teamRepository.persist(team);
    }
}
