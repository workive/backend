package app.teamwize.api.scheduling.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.scheduling.model.ScheduledJob;
import app.teamwize.api.scheduling.model.ScheduledJobExecution;
import app.teamwize.api.scheduling.model.entity.ScheduledJobEntity;
import app.teamwize.api.scheduling.model.entity.ScheduledJobExecutionEntity;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface ScheduledJobMapper {

    ScheduledJob toScheduledJob(ScheduledJobEntity entity);
    ScheduledJobExecution toScheduledJobExecution(ScheduledJobExecutionEntity entity);

}
