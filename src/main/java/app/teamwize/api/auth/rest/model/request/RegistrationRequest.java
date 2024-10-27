package app.teamwize.api.auth.rest.model.request;


import app.teamwize.api.base.validator.PhoneNumber;
import app.teamwize.api.base.validator.TimeZone;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegistrationRequest(
        @Nonnull
        @NotBlank(message = "organization.first_name.blank")
        @Size(min = 1, max = 255, message = "organization.first_name.size")
        String firstName,

        @Nonnull
        @NotBlank(message = "organization.last_name.blank")
        @Size(min = 1, max = 255, message = "organization.last_name.size")
        String lastName,

        @Nonnull
        @NotBlank(message = "organization.name.blank")
        @Size(min = 3, max = 100, message = "organization.name.size")
        String organizationName,

        @Nonnull
        @PhoneNumber
        String phone,

        @Nonnull
        @NotBlank(message = "organization.country.blank")
        @Size(min = 2, max = 2, message = "organization.country.size")
        String country,

        @Nullable
        @TimeZone(message = "site.timezone.invalid")
        String timezone,

        @Nonnull
        @NotBlank(message = "organization.email.blank")
        @Email(message = "organization.email.email_format")
        @Size(min = 5, max = 255, message = "organization.email.size")
        String email,

        @Nonnull
        @NotBlank(message = "organization.password.blank")
        @Size(min = 6, max = 32, message = "organization.password.size")
        String password
) {}