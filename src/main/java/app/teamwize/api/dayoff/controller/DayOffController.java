package app.teamwize.api.dayoff.controller;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.base.domain.model.response.PagedResponse;
import app.teamwize.api.dayoff.mapper.DayOffMapper;
import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.base.mapper.PagedResponseMapper;
import app.teamwize.api.dayoff.domain.request.DayOffCreateRequest;
import app.teamwize.api.dayoff.domain.request.DayOffFilterRequest;
import app.teamwize.api.dayoff.domain.request.DayOffUpdateRequest;
import app.teamwize.api.dayoff.domain.response.DayOffResponse;
import app.teamwize.api.dayoff.exception.DayOffNotFoundException;
import app.teamwize.api.dayoff.exception.DayOffUpdateStatusFailedException;
import app.teamwize.api.dayoff.service.DayOffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("days-off")
@RequiredArgsConstructor
public class DayOffController {

    private final DayOffService dayOffService;
    private final SecurityService securityService;
    private final DayOffMapper dayOffMapper;
    private final PagedResponseMapper pagedResponseMapper;


    @PostMapping
    public DayOffResponse create(@RequestBody DayOffCreateRequest request) {
        var dayOff = dayOffService.createDayOff(securityService.getUserOrganizationId(), securityService.getUserId(), request);
        return dayOffMapper.toDayOffResponse(dayOff);
    }

    @GetMapping
    public PagedResponse<DayOffResponse> getDaysOff(@ParameterObject @Valid PaginationRequest pagination,
                                                    @ParameterObject @Valid DayOffFilterRequest filters) {
        var daysOff = dayOffService.getDaysOff(securityService.getUserOrganizationId(),filters, pagination);
        return pagedResponseMapper.toPagedResponse(
                dayOffMapper.toDayOffResponseList(daysOff.getContent()),
                daysOff.getNumber(),
                daysOff.getSize(),
                daysOff.getTotalPages(),
                daysOff.getTotalElements()
        );
    }

    @GetMapping("mine")
    public PagedResponse<DayOffResponse> getMineDaysOff(@ParameterObject PaginationRequest pagination) {
        var daysOff = dayOffService.getDaysOff(securityService.getUserOrganizationId(), securityService.getUserId(), pagination);
        return pagedResponseMapper.toPagedResponse(
                dayOffMapper.toDayOffResponseList(daysOff.getContent()),
                daysOff.getNumber(),
                daysOff.getSize(),
                daysOff.getTotalPages(),
                daysOff.getTotalElements()
        );
    }

    @PutMapping("{id}")
    public DayOffResponse updateDayOff(@PathVariable Long id, @RequestBody DayOffUpdateRequest request) throws DayOffNotFoundException, DayOffUpdateStatusFailedException {
        return dayOffMapper.toDayOffResponse(dayOffService.updateDayOff(securityService.getUserId(), id, request));
    }

    @GetMapping("{id}")
    public DayOffResponse getDayOff(@PathVariable Long id) throws DayOffNotFoundException {
        return dayOffMapper.toDayOffResponse(dayOffService.getDayOff(securityService.getUserId(), id));
    }
}
