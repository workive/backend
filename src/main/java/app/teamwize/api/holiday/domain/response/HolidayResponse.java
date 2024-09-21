package app.teamwize.api.holiday.domain.response;

import java.time.LocalDate;

public record HolidayResponse(Long id, String description, LocalDate date, String country) {
}
