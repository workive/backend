package app.teamwize.api.leave.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class LeaveNotFoundException extends NotFoundException {

    public LeaveNotFoundException(String message) {
        super(message);
    }
}
