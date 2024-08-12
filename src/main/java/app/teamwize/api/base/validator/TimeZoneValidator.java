package app.teamwize.api.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.time.ZoneId;

@Slf4j
@Component
public class TimeZoneValidator implements ConstraintValidator<TimeZone, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return ZoneId.getAvailableZoneIds().contains(value);
    }
}
