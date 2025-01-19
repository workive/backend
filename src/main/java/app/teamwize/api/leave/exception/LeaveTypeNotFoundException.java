package app.teamwize.api.leave.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class LeaveTypeNotFoundException extends NotFoundException {
    public LeaveTypeNotFoundException(String message) {
        super(message);
    }
}
