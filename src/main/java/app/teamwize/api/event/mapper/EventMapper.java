package app.teamwize.api.event.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.event.entity.EventEntity;
import app.teamwize.api.event.entity.EventExecutionEntity;
import app.teamwize.api.event.model.Event;
import app.teamwize.api.event.model.EventExecution;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface EventMapper {

    Event toEvent(EventEntity eventEntity);

    EventEntity toEventEntity(Event event);

    EventExecution toEventExecution(EventExecutionEntity entity);



}
