package app.teamwize.api.event.rest.controller;

import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.base.domain.model.Pagination;
import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.base.domain.model.response.PagedResponse;
import app.teamwize.api.event.exception.EventNotFoundException;
import app.teamwize.api.event.rest.mapper.EventRestMapper;
import app.teamwize.api.event.rest.response.EventExecutionResponse;
import app.teamwize.api.event.rest.response.EventResponse;
import app.teamwize.api.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventRestMapper eventRestMapper;
    private final SecurityService securityService;

    @GetMapping
    public PagedResponse<EventResponse> getAllEvents(@ParameterObject @Valid PaginationRequest pagination) {
        var pagedEvents = eventService.getEvents(new Pagination(pagination.getPageNumber(), pagination.getPageSize()));
        return new PagedResponse<>(
                pagedEvents.contents().stream().map(eventRestMapper::toEventResponse).toList(),
                pagination.getPageNumber(),
                pagination.getPageSize(),
                pagedEvents.totalContents()
        );
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) throws EventNotFoundException {
        return eventRestMapper.toEventResponse(
                eventService.getEvent(securityService.getUserOrganizationId(), id)
        );
    }

    @GetMapping("{eventId}/executions")
    public PagedResponse<EventExecutionResponse> getEventExecutions(@PathVariable Long eventId, @ParameterObject @Valid PaginationRequest pagination) {
        var pagedEvents = eventService.getEventExecutions(eventId, new Pagination(pagination.getPageNumber(), pagination.getPageSize()));
        return new PagedResponse<>(
                pagedEvents.contents().stream().map(eventRestMapper::toEventExecutionResponse).toList(),
                pagination.getPageNumber(),
                pagination.getPageSize(),
                pagedEvents.totalContents()
        );
    }

}
