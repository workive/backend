package app.teamwize.api.organization.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrganizationNotFoundException extends BaseException {
    private final long organizationId;
}
