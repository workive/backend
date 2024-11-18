package app.teamwize.api.scheduling.rest.controller;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.base.domain.model.response.PagedResponse;
import app.teamwize.api.scheduling.rest.mapper.ScheduledJobRestMapper;
import app.teamwize.api.scheduling.rest.model.response.ScheduledJobExecutionResponse;
import app.teamwize.api.scheduling.rest.model.response.ScheduledJobResponse;
import app.teamwize.api.scheduling.service.ScheduledJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("scheduled-jobs")
@RequiredArgsConstructor
public class ScheduledJobController {

    private final ScheduledJobService scheduledJobService;
    private final ScheduledJobRestMapper scheduledJobRestMapper;

    @GetMapping
    public List<ScheduledJobResponse> getScheduledJobs() {
        return scheduledJobService.getScheduledJobs()
                .stream()
                .map(scheduledJobRestMapper::toScheduledJob).toList();
    }

    @GetMapping("{jobId}")
    public ScheduledJobResponse getScheduledJob(@PathVariable Long jobId) {
        return scheduledJobRestMapper.toScheduledJob(scheduledJobService.getScheduledJob(jobId));
    }

    @GetMapping("{jobId}/executions")
    public PagedResponse<ScheduledJobExecutionResponse> getScheduledJobExecutions(@PathVariable Long jobId, @ParameterObject @Valid PaginationRequest pagination) {
        var scheduledJobExecutions = scheduledJobService.getScheduledJobExecutions(jobId, pagination);
        return new PagedResponse<>(
                scheduledJobExecutions.contents().stream().map(scheduledJobRestMapper::toScheduledJobExecution).toList(),
                pagination.getPageNumber(),
                pagination.getPageSize(),
                scheduledJobExecutions.totalPages(),
                scheduledJobExecutions.totalContents()
        );
    }
}
