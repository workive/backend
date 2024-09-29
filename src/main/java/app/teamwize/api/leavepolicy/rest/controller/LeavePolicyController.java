package app.teamwize.api.leavepolicy.rest.controller;

import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.leavepolicy.rest.mapper.LeavePolicyRestMapper;
import app.teamwize.api.leavepolicy.rest.model.request.LeavePolicyCreateRequest;
import app.teamwize.api.leavepolicy.rest.model.response.LeavePolicyResponse;
import app.teamwize.api.leavepolicy.service.LeavePolicyService;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leaves/policies")
@RequiredArgsConstructor
public class LeavePolicyController {

    private final LeavePolicyService leavePolicyService;
    private final LeavePolicyRestMapper LeavePolicyRestMapper;
    private final SecurityService securityService;

    @PostMapping
    public LeavePolicyResponse createLeaveType(@RequestBody LeavePolicyCreateRequest request) throws OrganizationNotFoundException {
        var leaveType = leavePolicyService.createLeavePolicy(
                securityService.getUserOrganizationId(),
                LeavePolicyRestMapper.toNewLeavePolicy(request)
        );
        return LeavePolicyRestMapper.toResponse(leaveType);
    }

    @GetMapping
    public List<LeavePolicyResponse> getLeavePolicies() {
        var leaveTypes = leavePolicyService.getLeavePolicies(securityService.getUserOrganizationId());
        return leaveTypes.stream()
                .map(LeavePolicyRestMapper::toResponse)
                .toList();
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteLeavePolicy(@PathVariable Long id) {
        leavePolicyService.deleteLeavePolicy(securityService.getUserOrganizationId(), id);
    }




}
