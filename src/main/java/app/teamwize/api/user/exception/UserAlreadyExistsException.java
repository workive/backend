package app.teamwize.api.user.exception;


import app.teamwize.api.base.exception.BaseException;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
