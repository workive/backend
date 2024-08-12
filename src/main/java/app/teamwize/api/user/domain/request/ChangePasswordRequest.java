package app.teamwize.api.user.domain.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class ChangePasswordRequest {
    @NotEmpty(message = "user.change_password.blank")
    @Size(min = 1, max = 32, message = "user.change_password.size")
    private String currentPassword;
    @NotEmpty(message = "user.change_password.blank")
    @Size(min = 1, max = 32, message = "user.change_password.size")
    private String newPassword;
}
