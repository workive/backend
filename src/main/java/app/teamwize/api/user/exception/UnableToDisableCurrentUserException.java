package app.teamwize.api.user.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnableToDisableCurrentUserException extends BaseException {
    private final long targetUserId;
}
