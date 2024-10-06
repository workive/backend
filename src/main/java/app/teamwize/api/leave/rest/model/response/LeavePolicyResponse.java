package app.teamwize.api.leave.rest.model.response;

import java.util.List;

public record LeavePolicyResponse(
        Long id,
        String name,
        List<LeavePolicyActivatedTypeResponse> activatedTypes,
        boolean isDefault) {
}
