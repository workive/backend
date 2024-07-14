package app.workive.api.organization.domain.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record OrganizationUpdateRequest(
        @NotBlank(message = "organization.name.blank")
        @Size(min = 3, max = 100, message = "organization.name.size")
        String name,
        String timezone,
        String country,
        Map<String, Object> metadata,
        List<DayOfWeek> workingDays,

        DayOfWeek weekFirstDay
) {
}