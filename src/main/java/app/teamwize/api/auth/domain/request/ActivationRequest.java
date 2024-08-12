package app.teamwize.api.auth.domain.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class ActivationRequest {

    @NotBlank(message = "activation.password")
    @Size(min = 1, max = 32, message = "activation.password.size")
    private String password;
}
