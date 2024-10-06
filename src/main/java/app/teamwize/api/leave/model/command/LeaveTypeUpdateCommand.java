package app.teamwize.api.leave.model.command;

import app.teamwize.api.leave.model.LeaveTypeCycle;

public record LeaveTypeUpdateCommand(
        String name,
        LeaveTypeCycle cycle) {
}
