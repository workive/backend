package app.teamwize.api.leavepolicy.model;

import java.util.List;

public record NewLeavePolicy(
        String name,
        List<NewLeaveType> types,
        Boolean isDefault) {
}
