package app.teamwize.api.event.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class EventNotFoundException extends NotFoundException {


    public EventNotFoundException(String message) {
        super(message);
    }
}
