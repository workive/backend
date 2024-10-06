package app.teamwize.api.leave.model.command;

public record LeavePolicyActivatedTypeCommand(
        Long typeId,
        Integer amount,
        boolean requiresApproval) {
}
