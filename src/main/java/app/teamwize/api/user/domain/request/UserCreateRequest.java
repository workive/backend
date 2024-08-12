package app.teamwize.api.user.domain.request;

import app.teamwize.api.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private UserRole role;
}
