package app.teamwize.api.auth.rest.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ResetPasswordRequest {
    @NotBlank(message = "reset_password.email.blank")
    @Email(message = "reset_password.email.email_format")
    @Size(min = 5, max = 255, message = "reset_password.email.size")
    private String email;
}
