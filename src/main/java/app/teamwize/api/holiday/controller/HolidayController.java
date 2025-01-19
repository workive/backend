package app.teamwize.api.holiday.controller;

import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.base.exception.ServerException;
import app.teamwize.api.holiday.domain.request.HolidayCreateRequest;
import app.teamwize.api.holiday.domain.response.FetchedPublicHoliday;
import app.teamwize.api.holiday.domain.response.HolidayResponse;
import app.teamwize.api.holiday.domain.response.HolidaysOverviewResponse;
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
    public List<FetchedPublicHoliday> fetchPublicHoliday(Integer year, String countryCode) throws OrganizationNotFoundException, ServerException {
        return holidayService.fetchPublicHolidays(securityService.getUserOrganizationId(), year, countryCode);
    }

    @GetMapping
    public List<HolidaysOverviewResponse> getOverview() {
        var holidays = holidayService.getOverview(securityService.getUserOrganizationId());
        return holidays.stream().map(holidayMapper::toHolidaysOverview).toList();
    }

    @GetMapping("{countryCode}/{year}")
    public List<HolidayResponse> getHolidays(@PathVariable Integer year, @PathVariable String countryCode) {
        var holidays = holidayService.getHolidays(securityService.getUserOrganizationId(), year, countryCode);
        return holidayMapper.toHolidayResponses(holidays);
    }

}
