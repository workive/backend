package app.teamwize.api.notification.service.notifier;

import app.teamwize.api.notification.model.NotificationChannel;
import app.teamwize.api.notification.model.NotificationEvent;
import org.springframework.stereotype.Component;


@Component
public class EmailNotifier implements Notifier {
    @Override
    public boolean accepts(NotificationChannel channel) {
        return channel == NotificationChannel.EMAIL;
    }

    @Override
    public void notify(NotificationEvent event) {

    }
}
