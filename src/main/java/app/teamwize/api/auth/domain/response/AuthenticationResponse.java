package app.teamwize.api.auth.domain.response;

import app.teamwize.api.user.domain.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
    UserResponse user;
}
