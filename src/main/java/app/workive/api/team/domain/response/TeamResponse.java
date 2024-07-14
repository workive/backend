package app.workive.api.team.domain.response;

import java.util.Map;

public record TeamResponse(Long id, String name, Map<String,Object> metadata) {
}
