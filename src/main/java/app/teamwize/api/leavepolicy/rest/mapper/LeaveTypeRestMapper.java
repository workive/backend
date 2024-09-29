package app.teamwize.api.leavepolicy.rest.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.leavepolicy.model.NewLeaveType;
import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import app.teamwize.api.leavepolicy.rest.model.request.LeaveTypeCreateRequest;
import app.teamwize.api.leavepolicy.rest.model.response.LeaveTypeResponse;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface LeaveTypeRestMapper {

    LeaveTypeResponse toResponse(LeaveType leaveType);


    NewLeaveType toNewLeaveType(LeaveTypeCreateRequest request);
}
