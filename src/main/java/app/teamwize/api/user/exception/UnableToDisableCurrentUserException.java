package app.teamwize.api.user.exception;

import app.teamwize.api.base.exception.UnprocessableEntityException;

public class UnableToDisableCurrentUserException extends UnprocessableEntityException {
    public UnableToDisableCurrentUserException(String message) {
        super(message);
    }
}
