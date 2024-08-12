package app.teamwize.api.dayoff.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DayOffNotFoundException extends BaseException {
    private final long dayOffId;
}
