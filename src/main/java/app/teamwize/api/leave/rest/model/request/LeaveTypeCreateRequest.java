package app.teamwize.api.leave.rest.model.request;

import app.teamwize.api.leave.model.LeaveTypeCycle;

public record LeaveTypeCreateRequest(
        String name,
        LeaveTypeCycle cycle,
        Integer amount,
        boolean requiresApproval) {
}
