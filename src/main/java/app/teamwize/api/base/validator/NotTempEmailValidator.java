package app.teamwize.api.base.validator;

import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Component
class NotTempEmailValidator extends AbstractEmailValidator<NotTempEmail> {

    private static Set<String> blackList;

    private final Resource resourceFile;

    public NotTempEmailValidator(@Value("classpath:blacklist_emails.txt") Resource resourceFile) {
        this.resourceFile = resourceFile;
    }

    @Override
    public void initialize(NotTempEmail emailAnnotation) {
        super.initialize(emailAnnotation);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceFile.getInputStream()));
            String line;
            if (blackList == null) {
                synchronized (this) {
                    synchronized (this) {
                        blackList = new HashSet<>();
                        while ((line = bufferedReader.readLine()) != null) {
                            blackList.add(line.trim().toLowerCase());
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Could not initiate black-listed emails from its file", e);
        }
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (!super.isValid(value, context) || value == null) {
            return true;
        }
        String email = value.toString();
        if (!this.checkFormat(email)) {
            return true;
        }
        String domain = email.substring(email.lastIndexOf('@') + 1).trim().toLowerCase();
        return !blackList.contains(domain);
    }

    private boolean checkFormat(String email) {
        if (email == null || email.trim().length() == 0 || email.lastIndexOf('@') == -1) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        return Pattern.compile(ePattern).matcher(email).matches();
    }
}