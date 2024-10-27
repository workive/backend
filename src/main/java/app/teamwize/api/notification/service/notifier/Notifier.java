package app.teamwize.api.notification.service.notifier;

import app.teamwize.api.notification.model.NotificationChannel;
import app.teamwize.api.notification.model.NotificationEvent;

public interface Notifier {

    boolean accepts(NotificationChannel channel);

    void notify(NotificationEvent event);

}
