package app.teamwize.api.team.domain.event;

import app.teamwize.api.team.domain.TeamStatus;
import app.teamwize.api.team.domain.entity.Team;

import java.util.Map;

public record TeamEventPayload(
        Long id,
        String name,
        TeamStatus status,
        Map<String, Object> metadata) {

    public TeamEventPayload(Team team) {
        this(team.getId(), team.getName(), team.getStatus(), team.getMetadata());
    }
}
