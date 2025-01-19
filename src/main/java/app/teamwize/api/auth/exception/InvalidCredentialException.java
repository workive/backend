package app.teamwize.api.auth.exception;

import app.teamwize.api.base.exception.BaseException;

public class InvalidCredentialException extends BaseException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
