package app.teamwize.api.holiday.controller;

import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.holiday.domain.exception.PublicHolidayProviderConnectionBrokenException;
import app.teamwize.api.holiday.domain.response.FetchedPublicHoliday;
import app.teamwize.api.holiday.domain.request.HolidayCreateRequest;
import app.teamwize.api.holiday.domain.response.HolidayResponse;
import app.teamwize.api.holiday.mapper.HolidayMapper;
import app.teamwize.api.holiday.service.HolidayService;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;
    private final SecurityService securityService;
    private final HolidayMapper holidayMapper;

    @PostMapping("batch")
    public List<HolidayResponse> createHolidays(@RequestBody List<HolidayCreateRequest> requests) throws OrganizationNotFoundException {
        var holidays = holidayService.createHolidays(securityService.getUserOrganizationId(), requests);
        return holidayMapper.toHolidayResponses(holidays);
    }

    @GetMapping("fetch")
    public List<FetchedPublicHoliday> fetchPublicHoliday(Integer year) throws OrganizationNotFoundException, PublicHolidayProviderConnectionBrokenException {
        return holidayService.fetchPublicHolidays(securityService.getUserOrganizationId(), year);
    }

    @GetMapping
    public List<HolidayResponse> getHolidays(Integer year, String country) {
        var holidays = holidayService.getHolidays(securityService.getUserOrganizationId(), year, country);
        return holidayMapper.toHolidayResponses(holidays);
    }

}
