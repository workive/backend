package app.teamwize.api.user.domain.response;


import app.teamwize.api.team.domain.response.TeamCompactResponse;
import app.teamwize.api.user.domain.UserRole;
import app.teamwize.api.user.domain.UserStatus;
import app.teamwize.api.organization.domain.response.OrganizationCompactResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@Data
@NoArgsConstructor
public  class UserResponse {
    @Nonnull
    private Long id;
    @Nonnull
    private UserStatus status;
    @Nonnull
    private UserRole role;
    @Nonnull
    private String email;
    @Nonnull
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String phone;
    @Nonnull
    private OrganizationCompactResponse organization;
    @Nonnull
    private TeamCompactResponse team;
}
