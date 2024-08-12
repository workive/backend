package app.teamwize.api.base.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:/i18n/default_errors",
                "classpath:/i18n/email_messages",
                "classpath:/i18n/validations",
                "classpath:/i18n/messages",
                "classpath:/i18n/errors"
        );
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
