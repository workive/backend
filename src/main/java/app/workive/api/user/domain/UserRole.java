package app.workive.api.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    EMPLOYEE("employee"),
    API("api");

    private final String name;
}