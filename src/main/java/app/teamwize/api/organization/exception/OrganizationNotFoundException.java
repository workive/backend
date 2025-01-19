package app.teamwize.api.organization.exception;

import app.teamwize.api.base.exception.NotFoundException;

public class OrganizationNotFoundException extends NotFoundException {

    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
