package app.teamwize.api.user.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends BaseException {


    private long organizationId;


    private long userId;


    private String email;

    public UserNotFoundException(long organizationId, long userId) {
        this.organizationId = organizationId;
        this.userId = userId;
    }

    public UserNotFoundException(String email) {
        this.email = email;
    }
}
