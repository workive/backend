package app.teamwize.api.leavepolicy.rest.model.response;

import java.util.List;

public record LeavePolicyResponse(
        Long id,
        String name,
        List<LeaveTypeResponse> types,
        boolean isDefault) {
}
