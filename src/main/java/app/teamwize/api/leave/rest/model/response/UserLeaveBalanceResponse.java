package app.teamwize.api.leave.rest.model.response;



import java.time.LocalDate;

public record UserLeaveBalanceResponse(
        LeavePolicyActivatedTypeResponse activatedType,
        Long usedAmount,
        Long totalAmount,
        LocalDate startedAt) {
}
