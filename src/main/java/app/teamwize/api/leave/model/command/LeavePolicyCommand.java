package app.teamwize.api.leave.model.command;

import java.util.List;

public record LeavePolicyCommand(
        String name,
        List<LeavePolicyActivatedTypeCommand> activatedTypes) {
}
