package app.teamwize.api.user.domain.request;

import app.teamwize.api.user.domain.UserStatus;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UserChangeStatusRequest {
    @NotNull
    private UserStatus status;
}
