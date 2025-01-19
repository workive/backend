package app.teamwize.api.base.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BaseException {

    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
