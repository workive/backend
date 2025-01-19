package app.teamwize.api.user.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
