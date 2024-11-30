package app.teamwize.api.notification.service;

import app.teamwize.api.event.entity.EventEntity;
import app.teamwize.api.event.model.EventExitCode;
import app.teamwize.api.event.model.EventType;
import app.teamwize.api.event.service.handler.EventHandler;
import app.teamwize.api.notification.service.notifier.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/*
    Steps :
    - Try to get trigger and get list of channels
    - Try to call notification service and make notifications
    - Try to persist the result of notification
    - Decide about the cases that coudnl't deliver one of them
    - WebHooks should be repeated multiple times
    - We should retry to broken ones
    - We can add metadata to the event to skip some of processing that already done like MetaData
 */

@Component
@RequiredArgsConstructor
public class NotificationEventHandler implements EventHandler {

    private final List<Notifier> notifiers;

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean accepts(EventType type) {
        return true;
    }


    @Override
    public EventExecutionResult process(EventEntity eventEntity) {
        return new EventExecutionResult(EventExitCode.SUCCESS, Map.of("notificationId", 1000L));
    }
}
