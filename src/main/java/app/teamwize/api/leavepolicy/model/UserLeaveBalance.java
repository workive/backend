package app.teamwize.api.leavepolicy.model;

import app.teamwize.api.leavepolicy.model.entity.LeaveType;

import java.time.LocalDate;

public record UserLeaveBalance(LeaveType type, Long usedAmount, Long totalAmount, LocalDate startedAt) {
}
