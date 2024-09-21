package app.teamwize.api.holiday.domain.request;

import java.time.LocalDate;

public record HolidayCreateRequest(String description, LocalDate date,String country) {
}
