package app.teamwize.api.auth.service;

import app.teamwize.api.notification.service.EmailService;
import app.teamwize.api.organization.domain.event.OrganizationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {

    private final EmailService emailService;

//    @EventListener
//    void notifyOrganizationAdmin(OrganizationCreatedEvent event) {
//       // emailService.sendEmail();
//    }

}
