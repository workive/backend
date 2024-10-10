package app.teamwize.api.leave.repository;

import app.teamwize.api.base.domain.entity.EntityStatus;
import app.teamwize.api.leave.model.entity.LeaveType;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends BaseJpaRepository<LeaveType, Long> {


    List<LeaveType> findByOrganizationId(Long organizationId);

    void deleteByOrganizationIdAndId(Long policyId, Long id);


    Optional<LeaveType> findByOrganizationIdAndId(Long organizationId, Long id);

    @Modifying
    @Query("update LeaveType  type set type.status=:status where type.organization.id=:organizationId and type.id=:id")
    void updateStatus(Long organizationId, Long id,EntityStatus status);
}
