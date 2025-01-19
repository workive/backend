package app.teamwize.api.team.domain.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
