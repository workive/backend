package app.teamwize.api.leave.model;

import app.teamwize.api.leave.model.entity.LeavePolicyActivatedType;

import java.time.LocalDate;

public record UserLeaveBalance(
        LeavePolicyActivatedType activatedType,
        Long usedAmount,
        Long totalAmount,
        LocalDate startedAt) {
}
