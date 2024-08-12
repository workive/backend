package app.teamwize.api.team.domain.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamNotFoundException extends BaseException {
    private final long organizationId;
    private final long teamId;

}
