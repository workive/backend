package app.teamwize.api.base.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {
    private final HttpStatus status;

    public BaseException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus status() {
        return status;
    }
}