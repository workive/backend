package app.teamwize.api.notification.entity;

import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.domain.entity.User;
import jakarta.persistence.*;

import java.util.Map;

//@Entity
//public class Notification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private NotificationTemplate template;
//
//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    private Organization organization;
//
//    @ElementCollection
//    private Map<String, String> parameters; // Placeholder values (e.g., user_fullName -> John Doe)
//
//    @Enumerated(EnumType.STRING)
//    private NotificationChannel channel;
//
//    private LocalDateTime triggeredAt;
//
//    // Getters and Setters
//}
