package app.teamwize.api.leavepolicy.rest.model.request;


import java.util.List;

public record LeavePolicyCreateRequest(
        String name,
        List<LeaveTypeCreateRequest> types,
        boolean isDefault) {
}

