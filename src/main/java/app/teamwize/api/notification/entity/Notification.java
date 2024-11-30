package app.teamwize.api.notification.entity;

import app.teamwize.api.notification.model.NotificationChannel;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private NotificationTemplate template;

    @ManyToOne
    private User user;

    @ManyToOne
    private Organization organization;

    @ElementCollection
    private Map<String, String> parameters; // Placeholder values (e.g., user_fullName -> John Doe)

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    private Instant triggeredAt;

    // Getters and Setters
}
