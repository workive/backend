package app.teamwize.api.leave.rest.model.request;

public record LeavePolicyActivatedTypeRequest(Long typeId, Integer amount, boolean requiresApproval) {

}
