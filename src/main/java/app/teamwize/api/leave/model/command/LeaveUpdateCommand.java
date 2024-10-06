package app.teamwize.api.leave.model.command;

import app.teamwize.api.leave.model.LeaveStatus;

public record LeaveUpdateCommand(LeaveStatus status) {
}
