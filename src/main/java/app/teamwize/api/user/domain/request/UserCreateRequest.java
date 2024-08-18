package app.teamwize.api.user.domain.request;

import app.teamwize.api.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UserCreateRequest(
        String email,
        String firstName,
        String lastName,
        String password,
        String phone,
        UserRole role,
        String timezone,
        String countryCode,
        Long teamId
) {
}