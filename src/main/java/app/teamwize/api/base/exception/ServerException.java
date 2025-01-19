package app.teamwize.api.base.exception;


import org.springframework.http.HttpStatus;

public class ServerException extends BaseException {
    public ServerException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}