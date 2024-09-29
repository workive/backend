package app.teamwize.api.leavepolicy.service;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.leavepolicy.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leavepolicy.model.LeaveTypeCycle;
import app.teamwize.api.leavepolicy.model.NewLeavePolicy;
import app.teamwize.api.leavepolicy.model.NewLeaveType;
import app.teamwize.api.leavepolicy.model.entity.LeavePolicy;
import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import app.teamwize.api.leavepolicy.repository.LeavePolicyRepository;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeavePolicyService {

    private final LeavePolicyRepository leavePolicyRepository;
    private final OrganizationService organizationService;

    @Transactional
    public LeavePolicy createLeavePolicy(Long organizationId, NewLeavePolicy newLeavePolicy) throws OrganizationNotFoundException {
        var organization = organizationService.getOrganization(organizationId);


        final var policy = new LeavePolicy()
                .setDefault(newLeavePolicy.isDefault())
                .setName(newLeavePolicy.name())
                .setStatus(EntityStatus.ACTIVE)
                .setOrganization(organization);

        var types = newLeavePolicy.types().stream().map(type -> new LeaveType()
                .setName(type.name())
                .setCycle(type.cycle())
                .setAmount(type.amount())
                .setStatus(EntityStatus.ACTIVE)
                .setRequiresApproval(type.requiresApproval())
                .setPolicy(policy)).toList();

        policy.setTypes(types);


        return leavePolicyRepository.persist(policy);
    }

    @Transactional
    public LeavePolicy createDefaultLeavePolicy(Long organizationId) throws OrganizationNotFoundException {
        var request = new NewLeavePolicy("Default-Policy",
                List.of(
                        new NewLeaveType("Vacation", LeaveTypeCycle.PER_MONTH, 2, true),
                        new NewLeaveType("PTO", LeaveTypeCycle.PER_MONTH, 2, true),
                        new NewLeaveType("Sick-Leave", LeaveTypeCycle.PER_YEAR, 30, false)
                ),
                true
        );
        return createLeavePolicy(organizationId, request);
    }

    public List<LeavePolicy> getLeavePolicies(Long organizationId) {
        return leavePolicyRepository.findByOrganizationId(organizationId);
    }

    @Transactional
    public void deleteLeavePolicy(Long organizationId, Long id) {
        leavePolicyRepository.deleteByOrganizationIdAndId(organizationId, id);
    }

    public LeavePolicy getLeavePolicy(Long organizationId, Long id) throws LeaveTypeNotFoundException {
        return leavePolicyRepository.findByOrganizationIdAndId(organizationId, id)
                .orElseThrow(() -> new LeaveTypeNotFoundException(id));
    }
}
