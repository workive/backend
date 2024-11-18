package app.teamwize.api.scheduling.service;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.base.domain.model.Paged;
import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.base.domain.model.response.PagedResponse;
import app.teamwize.api.scheduling.mapper.ScheduledJobMapper;
import app.teamwize.api.scheduling.model.ExecutionStatus;
import app.teamwize.api.scheduling.model.ScheduledJob;
import app.teamwize.api.scheduling.model.ScheduledJobExecution;
import app.teamwize.api.scheduling.model.entity.ScheduledJobEntity;
import app.teamwize.api.scheduling.model.entity.ScheduledJobExecutionEntity;
import app.teamwize.api.scheduling.repository.ScheduledJobExecutionRepository;
import app.teamwize.api.scheduling.repository.ScheduledJobRepository;
import app.teamwize.api.scheduling.rest.model.response.ScheduledJobResponse;
import app.teamwize.api.scheduling.service.runner.JobRunner;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledJobService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledJobService.class);


    private final ScheduledJobRepository scheduledJobRepository;
    private final ScheduledJobExecutionRepository scheduledJobExecutionRepository;
    private final List<JobRunner> jobRunners;
    private final ScheduledJobMapper scheduledJobMapper;


    @Transactional
    public void updateNextExecutionTime(ScheduledJobEntity job) {
        job.setLastExecutedAt(Instant.now());
        CronExpression cron = CronExpression.parse(job.getCronExpression());
        var nextExecution = cron.next(LocalDateTime.now());
        job.setNextExecutionAt(nextExecution.toInstant(ZoneOffset.UTC));
    }

    @Transactional
    @Scheduled(fixedDelay = 60_000)
    public void executeScheduledJobs() {
        var jobs = scheduledJobRepository.findByStatusAndNextExecutionAtBefore(EntityStatus.ACTIVE, Instant.now());
        for (var job : jobs) {
            try {
                var runnerOpt = jobRunners.stream().filter(jobRunner -> jobRunner.name().equals(job.getRunner())).findFirst();
                if (runnerOpt.isPresent()) {
                    var execution = new ScheduledJobExecutionEntity();
                    try {
                        var runner = runnerOpt.get();
                        var result = runner.execute(job.getMetadata());
                        execution.setStatus(result.status())
                                .setExecutedAt(Instant.now())
                                .setScheduledJob(job)
                                .setMetadata(result.metadata());
                    } catch (Exception exception) {
                        logger.error("Error in job execution", exception);
                        execution
                                .setStatus(ExecutionStatus.FAILED)
                                .setErrorMessage(exception.toString());
                    }
                    scheduledJobExecutionRepository.merge(execution);
                }
                updateNextExecutionTime(job);
                scheduledJobRepository.merge(job);
            } catch (Exception e) {
                logger.error("Exception in executing scheduled jobs", e);
            }
        }
    }

    public List<ScheduledJob> getScheduledJobs() {
        return scheduledJobRepository.findAll()
                .stream()
                .map(scheduledJobMapper::toScheduledJob)
                .toList();
    }

    public ScheduledJob getScheduledJob(Long jobId) {
        var scheduledJob = scheduledJobRepository.findById(jobId).orElseThrow(RuntimeException::new);
        return scheduledJobMapper.toScheduledJob(scheduledJob);
    }

    public Paged<ScheduledJobExecution> getScheduledJobExecutions(Long jobId, PaginationRequest pagination) {
        var sort = Sort.by("id").descending();
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);
        var pagedResponse = scheduledJobExecutionRepository.findByScheduledJobId(jobId, pageRequest);
        return new Paged<>(
                pagedResponse.getContent().stream().map(scheduledJobMapper::toScheduledJobExecution).toList(),
                pagination.getPageSize(),
                pagination.getPageNumber(),
                pagedResponse.getTotalPages(),
                pagedResponse.getTotalElements()
        );
    }
}
