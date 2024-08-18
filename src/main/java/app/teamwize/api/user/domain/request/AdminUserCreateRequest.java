package app.teamwize.api.user.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record AdminUserCreateRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        String timezone
) {}