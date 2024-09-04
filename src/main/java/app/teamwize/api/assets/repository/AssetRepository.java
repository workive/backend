package app.teamwize.api.assets.repository;

import app.teamwize.api.assets.domain.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByOrganizationIdAndId(Long siteId, Long id);

    List<Asset> findByOrganizationIdAndIdIn(Long siteId, List<Long> ids);

    Optional<Asset> findByOrganizationIdAndName(Long organizationId, String name);
}
