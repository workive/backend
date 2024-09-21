package app.teamwize.api.holiday.provider;


import app.teamwize.api.holiday.domain.response.FetchedPublicHoliday;

import java.util.List;

public interface PublicHolidayProvider {
    List<FetchedPublicHoliday> getPublicHolidays(String country, Integer year);
}
