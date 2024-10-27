package app.teamwize.api.notification.entity;

import jakarta.persistence.ElementCollection;

import java.time.Instant;
import java.util.List;

public class NotificationTemplate {

    private String content;
    private List<NotificationEvent> events;

    @ElementCollection
    private List<String> channels; // e.g. EMAIL, SLACK


}
