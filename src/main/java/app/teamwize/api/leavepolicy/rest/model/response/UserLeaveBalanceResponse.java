package app.teamwize.api.leavepolicy.rest.model.response;


import java.time.LocalDate;

public record UserLeaveBalanceResponse(LeaveTypeResponse type, Long usedAmount, Long totalAmount, LocalDate startedAt) {
}
