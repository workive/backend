package app.teamwize.api.team.controller;

import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.team.domain.exception.TeamNotFoundException;
import app.teamwize.api.team.domain.request.TeamCreateRequest;
import app.teamwize.api.team.domain.response.TeamResponse;
import app.teamwize.api.team.mapper.TeamMapper;
import app.teamwize.api.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final SecurityService securityService;

    @GetMapping
    public List<TeamResponse> getTeams(@ParameterObject @Valid PaginationRequest pagination) {
        return teamMapper.toTeamResponseList(teamService.getTeams(securityService.getUserOrganizationId(),pagination));
    }

    @PostMapping
    public TeamResponse createTeam(@RequestBody TeamCreateRequest request) throws OrganizationNotFoundException {
        return teamMapper.toTeamResponse(teamService.createTeam(securityService.getUserOrganizationId(), request));
    }

    @PutMapping("{id}")
    public TeamResponse updateTeam(@PathVariable Long id, @RequestBody TeamCreateRequest request) throws TeamNotFoundException {
        return teamMapper.toTeamResponse(teamService.updateTeam(securityService.getUserOrganizationId(), id, request));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void removeTeam(@PathVariable Long id) throws TeamNotFoundException {
        teamService.removeTeam(securityService.getUserOrganizationId(), id);
    }

}
