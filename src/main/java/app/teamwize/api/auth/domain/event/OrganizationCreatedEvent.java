package app.teamwize.api.auth.domain.event;

import app.teamwize.api.base.util.StringUtils;
import app.teamwize.api.event.model.EventPayload;

import java.util.Map;

public record OrganizationCreatedEvent() implements EventPayload {
    @Override
    public String name() {
        return StringUtils.toSnakeCase(getClass().getSimpleName());
    }

    @Override
    public Map<String, Object> payload() {
        return null;
    }
}
