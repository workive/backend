package app.teamwize.api.leave.exception;

import app.teamwize.api.base.exception.BaseException;
import app.teamwize.api.leave.model.LeaveStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LeaveUpdateStatusFailedException extends BaseException {
    private final long dayOffId;

    private final LeaveStatus currentStatus;
}
