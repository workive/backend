package app.teamwize.api.leavepolicy.rest.model.response;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.leavepolicy.model.LeaveTypeCycle;

public record LeaveTypeResponse(
        Long id,
        String name,
        LeaveTypeCycle cycle,
        Integer amount,
        EntityStatus status,
        boolean requiresApproval) {
}
