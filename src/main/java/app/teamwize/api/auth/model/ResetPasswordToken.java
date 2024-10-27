package app.teamwize.api.auth.model;

import lombok.Data;

@Data
public class ResetPasswordToken {

    private Long userId;
    private String email;
    private String language;
}