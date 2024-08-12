package app.teamwize.api.user.domain.event;

import app.teamwize.api.user.domain.UserRole;
import lombok.Data;

@Data
public class UserInvitedEvent {
    private Long userId;
    private Long organizationId;
    private Long siteId;
    private UserRole role;
    private String email;
    private String language;
}
