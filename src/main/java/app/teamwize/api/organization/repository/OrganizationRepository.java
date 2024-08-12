package app.teamwize.api.organization.repository;


import app.teamwize.api.organization.domain.entity.Organization;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;

public interface OrganizationRepository extends BaseJpaRepository<Organization, Long> {


}
