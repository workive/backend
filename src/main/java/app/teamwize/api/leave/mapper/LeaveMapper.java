package app.teamwize.api.leave.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.leave.domain.entity.Leave;
import app.teamwize.api.leave.domain.response.LeaveResponse;
import app.teamwize.api.leavepolicy.model.UserLeaveBalance;
import app.teamwize.api.leavepolicy.rest.model.response.UserLeaveBalanceResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = DefaultMapperConfig.class)
public interface LeaveMapper {

    LeaveResponse toDayOffResponse(Leave leave);

    List<LeaveResponse> toDayOffResponseList(List<Leave> leaves);

    UserLeaveBalanceResponse toResponse(UserLeaveBalance balance);


}
