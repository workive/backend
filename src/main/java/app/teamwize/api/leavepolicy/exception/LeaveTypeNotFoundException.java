package app.teamwize.api.leavepolicy.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LeaveTypeNotFoundException extends BaseException {
    private final long id;
}
