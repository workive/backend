package app.teamwize.api.leavepolicy.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface LeaveTypeMapper {
    LeaveType toLeaveType(LeaveType entity);
}
