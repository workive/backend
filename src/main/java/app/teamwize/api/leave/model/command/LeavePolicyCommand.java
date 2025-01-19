package app.teamwize.api.leave.model.command;

import app.teamwize.api.leave.model.LeavePolicyStatus;

import java.util.List;

public record LeavePolicyCommand(
        String name,
        LeavePolicyStatus status,
        List<LeavePolicyActivatedTypeCommand> activatedTypes) {
}
