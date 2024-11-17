package app.teamwize.api.event.exception;

import app.teamwize.api.base.exception.BaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventNotFoundException extends BaseException {

    private final Long id;

}
