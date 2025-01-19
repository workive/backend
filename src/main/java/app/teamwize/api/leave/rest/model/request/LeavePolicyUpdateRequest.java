package app.teamwize.api.leave.rest.model.request;


import app.teamwize.api.leave.model.LeavePolicyStatus;

import java.util.List;

public record LeavePolicyUpdateRequest(
        String name,
        LeavePolicyStatus status,
        List<LeavePolicyActivatedTypeRequest> activatedTypes) {
}

