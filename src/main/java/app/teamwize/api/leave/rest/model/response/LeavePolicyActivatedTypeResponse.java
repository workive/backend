package app.teamwize.api.leave.rest.model.response;

import app.teamwize.api.base.domain.entity.EntityStatus;

public record LeavePolicyActivatedTypeResponse(
        Long id,
        LeaveTypeResponse type,
        Integer amount,
        Boolean requiresApproval,
        EntityStatus status) {
}
