package app.teamwize.api.holiday.provider;

import app.teamwize.api.holiday.domain.response.FetchedPublicHoliday;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public class OpenHolidayProvider implements PublicHolidayProvider {

    private final RestClient restClient;

    record OpenHolidayResponse(
            String id,
            LocalDate startDate,
            String endDate,
            String type,
            List<OpenHolidayResponseName> name,
            boolean nationwide) {
    }

    record OpenHolidayResponseName(
            String language,
            String text) {
    }


    @Override
    public List<FetchedPublicHoliday> getPublicHolidays(String country, Integer year) {
        var startDate = LocalDate.of(year, 1, 1);
        var endDate = startDate.plusYears(1).minusDays(1);
        List<OpenHolidayResponse> publicHolidays = this.restClient.get().uri(uriBuilder -> uriBuilder
                        .path("/PublicHolidays")
                        .queryParam("countryIsoCode", country)
                        .queryParam("languageIsoCode", "EN")
                        .queryParam("validFrom", startDate.toString())
                        .queryParam("validTo", endDate.toString())
                        .build())

                .accept(APPLICATION_JSON).retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return publicHolidays
                .stream()
                .map(this::toFetchedPublicHoliday)
                .toList();
    }


    private FetchedPublicHoliday toFetchedPublicHoliday(OpenHolidayResponse response) {
        return new FetchedPublicHoliday(response.startDate, response.type, response.name.get(0).text);
    }
}
