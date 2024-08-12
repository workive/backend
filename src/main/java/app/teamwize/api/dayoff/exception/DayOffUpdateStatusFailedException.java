package app.teamwize.api.dayoff.exception;

import app.teamwize.api.base.exception.BaseException;
import app.teamwize.api.dayoff.domain.DayOffStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DayOffUpdateStatusFailedException extends BaseException {
    private final long dayOffId;

    private final DayOffStatus currentStatus;
}
