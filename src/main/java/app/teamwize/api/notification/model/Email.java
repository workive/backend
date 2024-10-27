package app.teamwize.api.notification.model;

import java.util.Map;

/**
 * Represents the data required to send an email.
 *
 * @param to Recipient's email address.
 * @param subject Subject of the email.
 * @param content Template name to be used (e.g., "welcome-email").
 */
public record Email(
        String to,
        String subject,
        String content
) {}