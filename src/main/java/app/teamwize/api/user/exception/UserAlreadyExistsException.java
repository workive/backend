package app.teamwize.api.user.exception;


import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UserAlreadyExistsException extends BaseException {

    public static final String ERROR_CODE = "user.email_already_exists";

    private final String email;

}
