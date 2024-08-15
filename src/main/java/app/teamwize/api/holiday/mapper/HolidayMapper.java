package app.teamwize.api.holiday.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.holiday.domain.entity.Holiday;
import app.teamwize.api.holiday.domain.response.HolidayResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = DefaultMapperConfig.class)
public interface HolidayMapper {
    List<HolidayResponse> toHolidayResponses(List<Holiday> holidays);
}
