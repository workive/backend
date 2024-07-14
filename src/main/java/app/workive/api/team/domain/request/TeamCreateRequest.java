package app.workive.api.team.domain.request;

import java.util.Map;

public record TeamCreateRequest(String name, Map<String,Object> metadata) {
}
