package app.teamwize.api.user.domain.response;


import app.teamwize.api.team.domain.response.TeamCompactResponse;
import app.teamwize.api.user.domain.UserRole;
import app.teamwize.api.user.domain.UserStatus;
import app.teamwize.api.organization.domain.response.OrganizationCompactResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;


public record UserResponse(
        @Nonnull Long id,
        @Nonnull UserStatus status,
        @Nonnull UserRole role,
        @Nonnull String email,
        @Nonnull String firstName,
        @Nullable String lastName,
        @Nullable String phone,
        @Nullable String timezone,
        @Nullable String countryCode,
        @Nonnull OrganizationCompactResponse organization,
        @Nonnull TeamCompactResponse team
) {
}