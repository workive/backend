package app.teamwize.api.dayoff.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.dayoff.domain.entity.DayOff;
import app.teamwize.api.dayoff.domain.response.DayOffResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = DefaultMapperConfig.class)
public interface DayOffMapper {

    DayOffResponse toDayOffResponse(DayOff dayoff);

    List<DayOffResponse> toDayOffResponseList(List<DayOff> dayoff);



}
