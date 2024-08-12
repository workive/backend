package app.teamwize.api.user.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
