package app.teamwize.api.leave.rest.model.request;

import app.teamwize.api.leave.model.LeaveTypeCycle;

public record LeaveTypeUpdateRequest(
        String name,
        LeaveTypeCycle cycle) {
}
