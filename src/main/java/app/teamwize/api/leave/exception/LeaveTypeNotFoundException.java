package app.teamwize.api.leave.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LeaveTypeNotFoundException extends BaseException {
    private final long id;
}
