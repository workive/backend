package app.teamwize.api.notification.service;

import app.teamwize.api.notification.exception.NotificationSendFailureException;
import app.teamwize.api.notification.model.Email;
import app.teamwize.api.notification.model.NotificationEvent;
import app.teamwize.api.notification.service.notifier.Notifier;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.user.domain.entity.User;
import io.pebbletemplates.pebble.PebbleEngine;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final PebbleEngine pebbleEngine;
    private final List<Notifier> notifiers;

    public void notify(Organization organization, User user, NotificationEvent event, Map<String, Object> params) throws NotificationSendFailureException {
        try {
            this.notifyViaEmail(organization, user, event, params);
        } catch (Exception ex) {
            throw new NotificationSendFailureException();
        }
    }


    private void notifyViaEmail(Organization organization, User user, NotificationEvent event, Map<String, Object> params) throws MessagingException, IOException {
        var template = switch (event) {
            case LEAVE_CREATED, LEAVE_APPROVED, LEAVE_REJECTED -> "leave-updated";
            case LEAVE_REQUESTED -> "leave-requested";
            case ORGANIZATION_CREATED -> "welcome";
            case LEAVE_PENDING_RESPONSE -> "leave_pending_response.html";
        };
        var title = switch (event) {
            case LEAVE_CREATED -> "";
            case LEAVE_APPROVED -> "";
            case LEAVE_PENDING_RESPONSE -> "";
            case LEAVE_REQUESTED -> "";
            case LEAVE_REJECTED -> "";
            case ORGANIZATION_CREATED -> "";
        };
        var content = getTemplate(template, params);
        this.emailService.sendEmail(new Email(user.getEmail(), title, content));
    }

    public String getTemplate(String name, Map<String, Object> params) throws IOException {
        var template = pebbleEngine.getTemplate(name);
        var writer = new StringWriter();
        template.evaluate(writer, params);
        return writer.toString();
    }


}
