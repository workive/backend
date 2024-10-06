package app.teamwize.api.leave.rest.model.request;

import app.teamwize.api.leave.model.LeaveStatus;

public record LeaveUpdateRequest(LeaveStatus status) {
}
