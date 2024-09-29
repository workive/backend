package app.teamwize.api.user.domain.request;

public record AdminUserCreateRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        String timezone,
        String country,
        Long leavePolicyId
) {}