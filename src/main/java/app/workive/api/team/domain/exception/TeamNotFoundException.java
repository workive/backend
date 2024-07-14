package app.workive.api.team.domain.exception;

import app.workive.api.base.exception.BaseException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamNotFoundException extends BaseException {
    private final long organizationId;
    private final long teamId;

}
