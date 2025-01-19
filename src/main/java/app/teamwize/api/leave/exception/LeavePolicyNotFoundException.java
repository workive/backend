package app.teamwize.api.leave.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class LeavePolicyNotFoundException extends NotFoundException {
    public LeavePolicyNotFoundException(String message) {
        super(message);
    }
}
