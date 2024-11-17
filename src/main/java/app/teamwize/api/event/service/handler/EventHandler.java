package app.teamwize.api.event.service.handler;

import app.teamwize.api.event.entity.EventEntity;
import app.teamwize.api.event.model.EventExitCode;
import app.teamwize.api.event.model.EventType;

import java.util.Map;

public interface EventHandler {
    String name();

    boolean accepts(EventType type);

    EventExecutionResult process(EventEntity eventEntity);


    record EventExecutionResult(EventExitCode exitCode, Map<String, Object> metadata) {
    }

}
