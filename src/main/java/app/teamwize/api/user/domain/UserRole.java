package app.teamwize.api.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ORGANIZATION_ADMIN("organization_admin"),
    EMPLOYEE("employee"),
    TEAM_ADMIN("team_admin"),
    API("api");

    private final String name;
}