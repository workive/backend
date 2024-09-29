package app.teamwize.api.holiday.service;

import app.teamwize.api.holiday.domain.exception.PublicHolidayProviderConnectionBrokenException;
import app.teamwize.api.holiday.domain.response.FetchedPublicHoliday;
import app.teamwize.api.holiday.domain.entity.Holiday;
import app.teamwize.api.holiday.domain.request.HolidayCreateRequest;
import app.teamwize.api.holiday.provider.PublicHolidayProvider;
import app.teamwize.api.holiday.repository.HolidayRepository;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final OrganizationService organizationService;
    private final PublicHolidayProvider publicHolidayProvider;

    @Transactional
    public List<Holiday> createHolidays(Long organizationId, List<HolidayCreateRequest> requests) throws OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);
        var holidays = requests.stream().map(holidayCreateRequest -> new Holiday()
                .setDescription(holidayCreateRequest.description())
                .setDate(holidayCreateRequest.date())
                .setCountry(holidayCreateRequest.country())
                .setOrganization(organization)
        ).toList();
        return holidayRepository.persistAll(holidays);
    }

    @Transactional
    public List<FetchedPublicHoliday> fetchPublicHolidays(Long organizationId, Integer year) throws OrganizationNotFoundException, PublicHolidayProviderConnectionBrokenException {
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        var organization = organizationService.getOrganization(organizationId);
        try {
            return publicHolidayProvider.getPublicHolidays(organization.getCountry(), year);
        } catch (Exception ex) {
            throw new PublicHolidayProviderConnectionBrokenException();
        }
    }

    public List<Holiday> getHolidays(Long organizationId, Integer year, String country) {
        var startDate = LocalDate.of(year, 1, 1);
        var endDate = startDate.plusYears(1);
        return holidayRepository.findByOrganizationIdAndCountryAndDateIsBetween(organizationId, country, startDate, endDate);
    }


    public List<Holiday> getHolidays(Long organizationId, LocalDate startDate,LocalDate endDate, String country) {
        return holidayRepository.findByOrganizationIdAndCountryAndDateIsBetween(organizationId, country, startDate, endDate);
    }
}
