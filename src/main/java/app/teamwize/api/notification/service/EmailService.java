package app.teamwize.api.notification.service;

import app.teamwize.api.notification.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.email.company-name}")
    private String companyName;

    @Value("${app.email.templates-path}")
    private String templatesPath;


    /**
     * Sends an email based on the provided Email record.
     *
     * @param email The Email record containing email details.
     * @throws MessagingException If there is a failure in sending the email.
     */
    public void sendEmail(Email email) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(email.to());
        helper.setSubject(email.subject());

        // Prepare the evaluation context
        var context = new Context();
        context.setVariables(email.variables());
        context.setVariable("company", companyName); // Inject companyName from application.yml

        // Process the HTML template using Thymeleaf
        var html = templateEngine.process(email.template() + ".html", context);

        helper.setText(html, true); // Set to true for HTML

        mailSender.send(message);
    }
}