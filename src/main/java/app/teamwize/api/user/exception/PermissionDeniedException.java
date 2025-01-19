package app.teamwize.api.user.exception;

import app.teamwize.api.base.exception.UnprocessableEntityException;

public class PermissionDeniedException extends UnprocessableEntityException {

    public PermissionDeniedException(String message) {
        super(message);
    }
}
