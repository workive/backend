package app.teamwize.api.leave.rest.model.request;


import java.util.List;

public record LeavePolicyCreateRequest(
        String name,
        List<LeavePolicyActivatedTypeRequest> activatedTypes) {
}

