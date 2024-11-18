package app.teamwize.api.event.rest.mapper;


import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.event.model.Event;
import app.teamwize.api.event.model.EventExecution;
import app.teamwize.api.event.rest.response.EventExecutionResponse;
import app.teamwize.api.event.rest.response.EventResponse;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface EventRestMapper {

    EventResponse toEventResponse(Event event);

    EventExecutionResponse toEventExecutionResponse(EventExecution eventExecution);

}
