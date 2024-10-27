package app.teamwize.api.auth.model;

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