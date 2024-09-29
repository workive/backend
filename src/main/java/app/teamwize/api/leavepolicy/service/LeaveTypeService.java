package app.teamwize.api.leavepolicy.service;

import app.teamwize.api.leavepolicy.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import app.teamwize.api.leavepolicy.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;


    public LeaveType getLeaveType(Long policyId, Long id) throws LeaveTypeNotFoundException {
        return leaveTypeRepository.findByPolicyIdAndId(policyId, id)
                .orElseThrow(() -> new LeaveTypeNotFoundException(id));
    }
}
