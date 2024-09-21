package app.teamwize.api.user.domain.request;


import app.teamwize.api.base.validator.PhoneNumber;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class UserUpdateRequest {

    @Email()
    private JsonNullable<String> email;

    @Size(min = 1, max = 100)
    private JsonNullable<String> firstName;

    @Size(min = 1, max = 100)
    private JsonNullable<String> lastName;

    private JsonNullable<Long> avatarAssetId;

    @PhoneNumber
    private JsonNullable<String> phone;
}
