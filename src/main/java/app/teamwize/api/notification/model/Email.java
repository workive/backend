package app.teamwize.api.notification.model;

import java.util.Map;

/**
 * Represents the data required to send an email.
 *
 * @param to Recipient's email address.
 * @param subject Subject of the email.
 * @param template Template name to be used (e.g., "welcome-email").
 * @param variables Map of variables to replace in the template.
 */
public record Email(
        String to,
        String subject,
        String template,
        Map<String, Object> variables
) {}