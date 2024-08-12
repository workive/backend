package app.teamwize.api.organization.listener;


import app.teamwize.api.organization.domain.event.OrganizationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationEventListener {


    @EventListener
    public void createCustomer(OrganizationCreatedEvent event) {
        try {
            var organizationId = event.getOrganization().getId();
            log.info("Customer created for organization : {}", organizationId);
        } catch (Exception ex) {
            log.error("Error in creating api key for the registered organization", ex);
        }
    }

}
