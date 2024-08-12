package app.teamwize.api.base.exception;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class NotNullFieldViolationException extends BaseException {


    private final String fieldName;
}
