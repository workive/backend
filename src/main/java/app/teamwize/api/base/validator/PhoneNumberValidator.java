package app.teamwize.api.base.validator;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

@Slf4j
@Component
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.isBlank()) {
            return true;
        }
        phoneNumber = phoneNumber.replace(" ", "").replace("-", "");
        return isPhoneValid(phoneNumber);
    }

    private boolean isPhoneValid(String rawPhoneNumber) {
        try {
            var matcher = pattern.matcher(rawPhoneNumber);
            return matcher.matches();
        } catch (Exception e) {
            log.debug("Failed to parse the phone number [{}]", rawPhoneNumber, e);
            return false;
        }
    }
}