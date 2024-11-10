package app.teamwize.api.scheduling.rest.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.scheduling.model.ScheduledJob;
import app.teamwize.api.scheduling.model.ScheduledJobExecution;
import app.teamwize.api.scheduling.rest.model.response.ScheduledJobExecutionResponse;
import app.teamwize.api.scheduling.rest.model.response.ScheduledJobResponse;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface ScheduledJobRestMapper {

    ScheduledJobResponse toScheduledJob(ScheduledJob entity);
    ScheduledJobExecutionResponse toScheduledJobExecution(ScheduledJobExecution entity);

}
