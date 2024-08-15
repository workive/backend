package app.teamwize.api.holiday.domain.response;

import java.time.LocalDate;

public record FetchedPublicHoliday(LocalDate date, String type, String name){}
