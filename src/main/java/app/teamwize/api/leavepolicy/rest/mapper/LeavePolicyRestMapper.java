package app.teamwize.api.leavepolicy.rest.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.leavepolicy.model.NewLeavePolicy;
import app.teamwize.api.leavepolicy.model.UserLeaveBalance;
import app.teamwize.api.leavepolicy.model.entity.LeavePolicy;
import app.teamwize.api.leavepolicy.rest.model.request.LeavePolicyCreateRequest;
import app.teamwize.api.leavepolicy.rest.model.response.LeavePolicyResponse;
import app.teamwize.api.leavepolicy.rest.model.response.UserLeaveBalanceResponse;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class, uses = {LeaveTypeRestMapper.class})
public interface LeavePolicyRestMapper {

    LeavePolicyResponse toResponse(LeavePolicy leaveType);


    UserLeaveBalanceResponse toResponse(UserLeaveBalance leaveType);

    NewLeavePolicy toNewLeavePolicy(LeavePolicyCreateRequest request);
}
