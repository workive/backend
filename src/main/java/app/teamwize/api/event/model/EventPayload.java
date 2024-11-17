package app.teamwize.api.event.model;

import java.util.Map;

public interface EventPayload {
    String name();

    Map<String, Object> payload();
}
