package app.teamwize.api.team.domain.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Team Not Found")
public class TeamNotFoundException extends BaseException {
    private final long organizationId;
    private final long teamId;

}
