package app.teamwize.api.user.domain.request;


import app.teamwize.api.base.validator.PhoneNumber;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class UserUpdateRequest {

    @Email(message = "user.email.email_format")
    @Size(min = 5, max = 255, message = "user.email.size")
    private JsonNullable<String> email;

    @Size(min = 1, max = 100, message = "user.full_name.size")
    private JsonNullable<String> firstName;

    @Size(min = 1, max = 100, message = "user.full_name.size")
    private JsonNullable<String> lastName;

    @PhoneNumber
    private JsonNullable<String> phone;
}
