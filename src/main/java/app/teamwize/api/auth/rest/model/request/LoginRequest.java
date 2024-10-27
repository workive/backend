package app.teamwize.api.auth.rest.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginRequest {

    @Nonnull
    @NotBlank(message = "login.email.blank")
    @Email(message = "login.email.email_format")
    private String email;

    @Nonnull
    @NotBlank(message = "login.email.blank")
    private String password;
}
