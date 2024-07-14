package app.workive.api.user.domain.response;


import app.workive.api.organization.domain.response.OrganizationCompactResponse;
import app.workive.api.team.domain.response.TeamCompactResponse;
import app.workive.api.team.domain.response.TeamResponse;
import app.workive.api.user.domain.UserRole;
import app.workive.api.user.domain.UserStatus;
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
