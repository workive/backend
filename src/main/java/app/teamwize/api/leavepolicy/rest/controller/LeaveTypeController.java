package app.teamwize.api.leavepolicy.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("leaves/types")
@RequiredArgsConstructor
public class LeaveTypeController {

//    private final LeaveTypeService leaveTypeService;
//    private final LeaveTypeRestMapper leaveTypeMapper;
//    private final SecurityService securityService;
//
//    @PostMapping
//    public LeaveTypeResponse createLeaveType(@RequestBody LeaveTypeCreateRequest request) throws OrganizationNotFoundException {
//        var leaveType = leaveTypeService.createLeaveType(
//                securityService.getUserOrganizationId(),
//                leaveTypeMapper.toNewLeaveType(request)
//        );
//        return leaveTypeMapper.toResponse(leaveType);
//    }
//
//    @GetMapping
//    public List<LeaveTypeResponse> getLeaveTypes() {
//        var leaveTypes = leaveTypeService.getLeaveTypes(securityService.getUserOrganizationId());
//        return leaveTypes.stream()
//                .map(leaveTypeMapper::toResponse)
//                .toList();
//    }
//
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("{id}")
//    public void deleteLeaveType(@PathVariable Long id) {
//        leaveTypeService.deleteLeaveType(securityService.getUserOrganizationId(), id);
//    }
//
//
//    @GetMapping("balance")
//    public List<UserLeaveBalanceResponse> getBalance() throws UserNotFoundException {
//        return leaveTypeService.getLeaveBalance(securityService.getUserOrganizationId(), securityService.getUserId())
//                .stream().map(leaveTypeMapper::toResponse)
//                .toList();
//    }

}
