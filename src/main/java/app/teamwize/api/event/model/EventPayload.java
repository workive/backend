package app.teamwize.api.event.model;

import java.util.Map;

public interface EventPayload {
    EventType name();

    Map<String, Object> payload();
}
