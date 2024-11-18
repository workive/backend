package app.teamwize.api.leave.rest.controller;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.base.domain.model.response.PagedResponse;
import app.teamwize.api.leave.rest.mapper.LeaveMapper;
import app.teamwize.api.auth.service.SecurityService;
import app.teamwize.api.base.mapper.PagedResponseMapper;
import app.teamwize.api.leave.rest.model.request.LeaveCreateRequest;
import app.teamwize.api.leave.rest.model.request.LeaveFilterRequest;
import app.teamwize.api.leave.rest.model.request.LeaveUpdateRequest;
import app.teamwize.api.leave.rest.model.response.LeaveResponse;
import app.teamwize.api.leave.exception.LeaveNotFoundException;
import app.teamwize.api.leave.exception.LeaveUpdateStatusFailedException;
import app.teamwize.api.leave.service.LeaveService;
import app.teamwize.api.leave.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leave.rest.model.response.UserLeaveBalanceResponse;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.user.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    private final SecurityService securityService;
    private final LeaveMapper leaveMapper;
    private final PagedResponseMapper pagedResponseMapper;


    @PostMapping
    public LeaveResponse create(@RequestBody LeaveCreateRequest request)
            throws UserNotFoundException, LeaveTypeNotFoundException, OrganizationNotFoundException {
        var dayOff = leaveService.createLeave(
                securityService.getUserOrganizationId(),
                securityService.getUserId(),
                leaveMapper.toCreateCommand(request)
        );
        return leaveMapper.toDayOffResponse(dayOff);
    }

    @GetMapping
    public PagedResponse<LeaveResponse> getDaysOff(@ParameterObject @Valid PaginationRequest pagination,
                                                   @ParameterObject @Valid LeaveFilterRequest filters) {
        var daysOff = leaveService.getLeaves(securityService.getUserOrganizationId(), filters, pagination);
        return pagedResponseMapper.toPagedResponse(
                leaveMapper.toDayOffResponseList(daysOff.getContent()),
                daysOff.getNumber(),
                daysOff.getSize(),
                daysOff.getTotalPages(),
                daysOff.getTotalElements()
        );
    }

    @GetMapping("mine")
    public PagedResponse<LeaveResponse> getMineDaysOff(@ParameterObject PaginationRequest pagination) {
        var daysOff = leaveService.getLeaves(securityService.getUserOrganizationId(), securityService.getUserId(), pagination);
        return pagedResponseMapper.toPagedResponse(
                leaveMapper.toDayOffResponseList(daysOff.getContent()),
                daysOff.getNumber(),
                daysOff.getSize(),
                daysOff.getTotalPages(),
                daysOff.getTotalElements()
        );
    }

    @GetMapping("mine/balance")
    public List<UserLeaveBalanceResponse> getBalance() throws UserNotFoundException, LeaveTypeNotFoundException {
        return leaveService.getLeaveBalance(securityService.getUserOrganizationId(), securityService.getUserId())
                .stream().map(leaveMapper::toResponse)
                .toList();
    }

    @GetMapping("{id}/balance")
    public List<UserLeaveBalanceResponse> getBalanceById(@PathVariable Long id) throws UserNotFoundException, LeaveTypeNotFoundException {
        return leaveService.getLeaveBalance(securityService.getUserOrganizationId(), id)
                .stream().map(leaveMapper::toResponse)
                .toList();
    }

    @PutMapping("{id}")
    public LeaveResponse updateDayOff(@PathVariable Long id, @RequestBody LeaveUpdateRequest request) throws LeaveNotFoundException, LeaveUpdateStatusFailedException, UserNotFoundException {
        return leaveMapper.toDayOffResponse(leaveService.updateLeave(securityService.getUserOrganizationId(), securityService.getUserId(), id, leaveMapper.toUpdateCommand(request)));
    }

    @GetMapping("{id}")
    public LeaveResponse getDayOff(@PathVariable Long id) throws LeaveNotFoundException {
        return leaveMapper.toDayOffResponse(leaveService.getLeave(securityService.getUserId(), id));
    }
}
