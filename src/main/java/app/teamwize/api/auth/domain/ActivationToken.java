package app.teamwize.api.auth.domain;

import app.teamwize.api.user.domain.UserRole;
import lombok.Data;

@Data
public class ActivationToken {
    private Long siteId;
    private Long userId;
    private String email;
    private String language;
    private UserRole role;
}