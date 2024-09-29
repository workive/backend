package app.teamwize.api.leavepolicy.repository;

import app.teamwize.api.leavepolicy.model.entity.LeavePolicy;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeavePolicyRepository extends BaseJpaRepository<LeavePolicy, Long> {


    @EntityGraph(attributePaths = {"organization", "types"}, type = EntityGraph.EntityGraphType.FETCH)
    List<LeavePolicy> findByOrganizationId(Long organizationId);

    void deleteByOrganizationIdAndId(Long organizationId, Long id);

    @EntityGraph(attributePaths = {"organization", "types"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<LeavePolicy> findByOrganizationIdAndId(Long organizationId, Long id);
}
