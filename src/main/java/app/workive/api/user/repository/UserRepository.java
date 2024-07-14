package app.workive.api.user.repository;

import app.workive.api.user.domain.entity.User;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseJpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = { "organization"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findByOrganizationIdAndId(long organizationId, long userId);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"organization"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = { "organization"}, type = EntityGraph.EntityGraphType.FETCH)
    List<User> findByOrganizationId(Long organization);
}
