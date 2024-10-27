package app.teamwize.api.auth.rest.model.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class ConfirmResetPasswordRequest {
    @NotBlank(message = "reset_password.password.blank")
    @Size(min = 1, max = 32, message = "reset_password.password.size")
    private String password;
}
