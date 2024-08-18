package app.teamwize.api.user.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.NoArgsConstructor;

public class PermissionDeniedException extends BaseException {
    private final long organizationId;
    public PermissionDeniedException(long organizationId) {
        this.organizationId = organizationId;
    }

}
