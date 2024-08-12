package app.teamwize.api.user.domain.event;

import lombok.Data;

@Data
public class UserPasswordResetEvent {
    private Long userId;
    private Long siteId;
    private String email;
    private String language;
}
