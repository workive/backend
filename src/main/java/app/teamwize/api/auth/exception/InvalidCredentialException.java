package app.teamwize.api.auth.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class InvalidCredentialException extends BaseException {


    private final String email;

}
