package app.teamwize.api.leave.rest.controller;

import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.leave.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leave.rest.mapper.LeaveTypeMapper;
import app.teamwize.api.leave.rest.model.request.LeaveTypeCreateRequest;
import app.teamwize.api.leave.rest.model.request.LeaveTypeUpdateRequest;
import app.teamwize.api.leave.rest.model.response.LeaveTypeResponse;
import app.teamwize.api.leave.service.LeaveTypeService;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leaves/types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;
    private final LeaveTypeMapper leaveTypeMapper;
    private final SecurityService securityService;

    @PostMapping
    public LeaveTypeResponse createLeaveType(@RequestBody LeaveTypeCreateRequest request) throws OrganizationNotFoundException {
        var leaveType = leaveTypeService.createLeaveType(
                securityService.getUserOrganizationId(),
                leaveTypeMapper.toNewLeaveType(request)
        );
        return leaveTypeMapper.toResponse(leaveType);
    }

    @GetMapping
    public List<LeaveTypeResponse> getLeaveTypes() {
        var leaveTypes = leaveTypeService.getLeaveTypes(securityService.getUserOrganizationId());
        return leaveTypes.stream()
                .map(leaveTypeMapper::toResponse)
                .toList();
    }


    @PutMapping("{id}")
    public LeaveTypeResponse updateLeaveType(@PathVariable Long id, @RequestBody LeaveTypeUpdateRequest request) throws LeaveTypeNotFoundException {
        var leaveType = leaveTypeService.updateLeaveType(
                securityService.getUserOrganizationId(),
                id,
                leaveTypeMapper.toUpdateCommand(request)
        );
        return leaveTypeMapper.toResponse(leaveType);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteLeaveType(@PathVariable Long id) {
        leaveTypeService.archiveLeaveType(securityService.getUserOrganizationId(), id);
    }

}
