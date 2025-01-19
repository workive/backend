package app.teamwize.api.user.exception;


import app.teamwize.api.base.exception.UnprocessableEntityException;

public class IncorrectPasswordException extends UnprocessableEntityException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

}
