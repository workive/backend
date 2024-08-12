package app.teamwize.api.auth.domain.request;


import app.teamwize.api.base.validator.PhoneNumber;
import app.teamwize.api.base.validator.TimeZone;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegistrationRequest {
    @Nonnull
    @NotBlank(message = "organization.first_name.blank")
    @Size(min = 1, max = 255, message = "organization.first_name.size")
    private String firstName;

    @Nonnull
    @NotBlank(message = "organization.last_name.blank")
    @Size(min = 1, max = 255, message = "organization.last_name.size")
    private String lastName;

    @Nonnull
    @NotBlank(message = "organization.name.blank")
    @Size(min = 3, max = 100, message = "organization.name.size")
    private String organizationName;

    @Nonnull
    @PhoneNumber
    private String phone;

    @Nonnull
    @NotBlank(message = "organization.country.blank")
    @Size(min = 2, max = 2, message = "organization.country.size")
    private String countryCode;

    @Nullable
    @TimeZone(message = "site.timezone.invalid")
    private String timezone;

    @Nonnull
    @NotBlank(message = "organization.email.blank")
    @Email(message = "organization.email.email_format")
    @Size(min = 5, max = 255, message = "organization.email.size")
    private String email;

    @Nonnull
    @NotBlank(message = "organization.password.blank")
    @Size(min = 6, max = 32, message = "organization.password.size")
    private String password;
}
