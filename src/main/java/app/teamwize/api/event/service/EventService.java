package app.teamwize.api.event.service;

import app.teamwize.api.event.entity.Event;
import app.teamwize.api.event.entity.EventExecution;
import app.teamwize.api.event.model.EventExecutionStatus;
import app.teamwize.api.event.model.EventExitCode;
import app.teamwize.api.event.model.EventStatus;
import app.teamwize.api.event.model.EventType;
import app.teamwize.api.event.repository.EventExecutionRepository;
import app.teamwize.api.event.repository.EventRepository;
import app.teamwize.api.event.service.handler.EventHandler;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventExecutionRepository executionRepository;
    private final List<EventHandler> eventHandlers;


    @Transactional
    public Event emmit(Long organizationId, EventType eventType, Map<String, Object> params, byte maxAttempts, LocalDateTime scheduledAt) {
        var executions = eventHandlers.stream().filter(eventHandler -> eventHandler.accepts(eventType)).map(eventHandler -> new EventExecution()
                .setStatus(EventExecutionStatus.PENDING)
                .setHandler(eventHandler.name())).toList();
        var event = new Event()
                .setOrganization(new Organization(organizationId))
                .setType(eventType)
                .setParams(params)
                .setStatus(EventStatus.PENDING)
                .setMaxAttempts(maxAttempts)
                .setScheduledAt(scheduledAt)
                .setExecutions(executions);
        return eventRepository.persist(event);
    }

    @Transactional
    public Event emmit(Long organizationId, EventType eventType, Map<String, Object> params) {
        return emmit(organizationId, eventType, params, (byte) 3, LocalDateTime.now());
    }


    // Maybe it's better to try forever to execute an event
    // There is no need to have exitCode for events they are not jobs

    @Transactional
    @Scheduled(fixedDelay = 60_000)
    public void processEvents() {
        var pendingEvents = eventRepository.findByStatus(EventStatus.PENDING);
        for (var pendingEvent : pendingEvents) {
            for (var execution : pendingEvent.getExecutions()) {
                var handlerOptional = eventHandlers.stream().filter(eventHandler -> eventHandler.name().equals(execution.getHandler())).findFirst();
                if (handlerOptional.isEmpty()) continue;
                var handler = handlerOptional.get();
                var executionResult = handler.process(pendingEvent);
                if (executionResult.exitCode() == EventExitCode.SUCCESS) {
                    execution.setStatus(EventExecutionStatus.FINISHED);
                } else {
                    execution.setStatus(EventExecutionStatus.RETRYING);
                }
                execution.setExitCode(executionResult.exitCode())
                        .setAttempts(execution.getAttempts() + 1)
                        .setMetadata(executionResult.metadata());

                if (execution.getAttempts() > pendingEvent.getMaxAttempts()) {
                    execution.setStatus(EventExecutionStatus.FINISHED);
                    execution.setExitCode(EventExitCode.RETRY_EXCEEDED);
                }
                executionRepository.update(execution);
            }
        }
    }

}
