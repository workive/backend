package app.teamwize.api.leavepolicy.model;

public record NewLeaveType(
        String name,
        LeaveTypeCycle cycle,
        Integer amount,
        boolean requiresApproval) {
}
