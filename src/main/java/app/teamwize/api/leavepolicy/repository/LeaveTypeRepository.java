package app.teamwize.api.leavepolicy.repository;

import app.teamwize.api.leavepolicy.model.entity.LeaveType;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends BaseJpaRepository<LeaveType, Long> {


    List<LeaveType> findByPolicyId(Long policyId);

    void deleteByPolicyIdAndId(Long policyId, Long id);

    Optional<LeaveType> findByPolicyIdAndId(Long policyId,Long id);

}
