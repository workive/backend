package app.teamwize.api.leavepolicy.rest.model.request;

import app.teamwize.api.leavepolicy.model.LeaveTypeCycle;

public record LeaveTypeCreateRequest(
        String name,
        LeaveTypeCycle cycle,
        Integer amount,
        boolean requiresApproval) {
}
