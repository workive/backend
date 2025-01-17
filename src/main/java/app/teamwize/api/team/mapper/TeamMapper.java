package app.teamwize.api.team.mapper;

import app.teamwize.api.team.domain.entity.Team;
import app.teamwize.api.team.domain.response.TeamResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamResponse toTeamResponse(Team team);

    List<TeamResponse> toTeamResponseList(List<Team> teams);
}
