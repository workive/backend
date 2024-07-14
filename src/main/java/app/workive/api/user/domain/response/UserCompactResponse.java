package app.workive.api.user.domain.response;

import app.workive.api.team.domain.response.TeamCompactResponse;
import app.workive.api.team.domain.response.TeamResponse;
import app.workive.api.user.domain.UserRole;
import app.workive.api.user.domain.UserStatus;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public record UserCompactResponse(
        @Nonnull
        Long id,
        @Nonnull
        UserStatus status,
        @Nonnull
        UserRole role,
        @Nonnull
        String email,
        @Nonnull
        String firstName,
        @Nullable
        String lastName,
        @Nullable
        String phone,
        @Nonnull
        TeamCompactResponse team) {
}
