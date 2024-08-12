package app.teamwize.api.team.service;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.organization.service.OrganizationService;
import app.teamwize.api.team.repository.TeamRepository;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.team.domain.TeamStatus;
import app.teamwize.api.team.domain.entity.Team;
import app.teamwize.api.team.domain.exception.TeamNotFoundException;
import app.teamwize.api.team.domain.request.TeamCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final OrganizationService organizationService;

    public List<Team> getTeams(Long organizationId, PaginationRequest pagination) {
        var sort = Sort.by("id").descending();
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);
        return teamRepository.findByOrganizationIdAndStatusIsIn(organizationId,List.of(TeamStatus.DEFAULT),pageRequest);
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
