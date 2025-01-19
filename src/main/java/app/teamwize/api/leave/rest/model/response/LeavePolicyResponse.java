package app.teamwize.api.leave.rest.model.response;

import app.teamwize.api.leave.model.LeavePolicyStatus;

import java.util.List;

public record LeavePolicyResponse(
        Long id,
        String name,
        LeavePolicyStatus status,
        List<LeavePolicyActivatedTypeResponse> activatedTypes) {
}
