package app.workive.api.organization.domain.response;

import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;


public record OrganizationResponse(
         Long id,
         String name,
         String timezone,
         String country,
         Map<String, Object> metadata,
         List<DayOfWeek> workingDays,
         DayOfWeek weekFirstDay) {

}
