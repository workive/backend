package app.teamwize.api.auth.domain.event;

import app.teamwize.api.user.domain.UserRole;
import app.teamwize.api.user.domain.entity.User;

public record UserEventPayload(
        Long id,
        UserRole role,
        String email,
        String password,
        String phone,
        String firstName,
        String lastName,
        String country,
        String timezone) {

    public UserEventPayload(User user) {
        this(user.getId(),
                user.getRole(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getFirstName(),
                user.getLastName(),
                user.getCountry(),
                user.getTimezone()
        );
    }
}