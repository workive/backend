package app.teamwize.api.leave.service;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.leave.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leave.model.LeavePolicyStatus;
import app.teamwize.api.leave.model.LeaveTypeCycle;
import app.teamwize.api.leave.model.command.LeavePolicyCommand;
import app.teamwize.api.leave.model.command.LeavePolicyActivatedTypeCommand;
import app.teamwize.api.leave.model.command.LeaveTypeCommand;
import app.teamwize.api.leave.model.entity.LeavePolicy;
import app.teamwize.api.leave.model.entity.LeavePolicyActivatedType;
import app.teamwize.api.leave.repository.LeavePolicyRepository;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeavePolicyService {

    private final LeavePolicyRepository leavePolicyRepository;
    private final OrganizationService organizationService;
    private final LeaveTypeService leaveTypeService;

    @Transactional
    public LeavePolicy createLeavePolicy(Long organizationId, Boolean isDefault, LeavePolicyCommand leavePolicyCommand) throws OrganizationNotFoundException, LeaveTypeNotFoundException {
        var organization = organizationService.getOrganization(organizationId);

        var policy = new LeavePolicy()
                .setName(leavePolicyCommand.name())
                .setStatus(isDefault ? LeavePolicyStatus.DEFAULT : LeavePolicyStatus.ACTIVE)
                .setOrganization(organization)
                .setActivatedTypes(new ArrayList<>());

        for (var activatedType : leavePolicyCommand.activatedTypes()) {
            var type = leaveTypeService.getLeaveType(organizationId, activatedType.typeId());
            policy.getActivatedTypes().add(new LeavePolicyActivatedType()
                    .setType(type)
                    .setPolicy(policy)
                    .setAmount(activatedType.amount())
                    .setRequiresApproval(activatedType.requiresApproval())
                    .setStatus(EntityStatus.ACTIVE)
            );
        }

        return leavePolicyRepository.persist(policy);
    }

    @Transactional
    public LeavePolicy createDefaultLeavePolicy(Long organizationId) throws OrganizationNotFoundException, LeaveTypeNotFoundException {
        var leaveTypes = leaveTypeService.createLeaveTypes(organizationId, List.of(
                new LeaveTypeCommand("Vacation", LeaveTypeCycle.PER_MONTH),
                new LeaveTypeCommand("PTO", LeaveTypeCycle.PER_MONTH),
                new LeaveTypeCommand("Sick-Leave", LeaveTypeCycle.PER_YEAR)
        ));
        var request = new LeavePolicyCommand("Default-Policy",
                List.of(
                        new LeavePolicyActivatedTypeCommand(leaveTypes.get(0).getId(), 2, true),
                        new LeavePolicyActivatedTypeCommand(leaveTypes.get(1).getId(), 2, true),
                        new LeavePolicyActivatedTypeCommand(leaveTypes.get(2).getId(), 30, false)
                )
        );
        return createLeavePolicy(organizationId, true, request);
    }

    public List<LeavePolicy> getLeavePolicies(Long organizationId) {
        return leavePolicyRepository.findByOrganizationIdAndStatusIsIn(organizationId, List.of(LeavePolicyStatus.ACTIVE));
    }

    public Optional<LeavePolicy> getDefaultLeavePolicy(Long organizationId) {
        var policies = leavePolicyRepository.findByOrganizationIdAndStatusIsIn(organizationId, List.of(LeavePolicyStatus.DEFAULT));
        if (policies.isEmpty()) return Optional.empty();
        return Optional.of(policies.get(0));
    }

    @Transactional
    public void updateDefaultPolicy(Long organizationId, Long id) {
        var defaultPolicyOptional = getDefaultLeavePolicy(organizationId);
        if (defaultPolicyOptional.isPresent()) {
            var defaultPolicy = defaultPolicyOptional.get();
            defaultPolicy.setStatus(LeavePolicyStatus.ACTIVE);
            leavePolicyRepository.update(defaultPolicy);
        }
        leavePolicyRepository.updateStatus(organizationId, id, LeavePolicyStatus.ARCHIVED);
    }

    @Transactional
    public void deleteLeavePolicy(Long organizationId, Long id) {
        leavePolicyRepository.updateStatus(organizationId, id, LeavePolicyStatus.ARCHIVED);
    }

    public LeavePolicy getLeavePolicy(Long organizationId, Long id) throws LeaveTypeNotFoundException {
        return leavePolicyRepository.findByOrganizationIdAndId(organizationId, id)
                .orElseThrow(() -> new LeaveTypeNotFoundException(id));
    }
}
