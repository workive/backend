package app.teamwize.api.leave.exception;

import app.teamwize.api.base.exception.ServerException;

public class LeaveUpdateStatusFailedException extends ServerException {
    public LeaveUpdateStatusFailedException(String message) {
        super(message);
    }
}
