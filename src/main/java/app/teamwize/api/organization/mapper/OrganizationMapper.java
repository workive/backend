package app.teamwize.api.organization.mapper;

import app.teamwize.api.base.config.DefaultMapperConfig;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.organization.domain.response.OrganizationCompactResponse;
import app.teamwize.api.organization.domain.response.OrganizationResponse;
import org.mapstruct.Mapper;


@Mapper(config = DefaultMapperConfig.class)
public interface OrganizationMapper {
    OrganizationResponse toResponse(Organization organization);

    OrganizationCompactResponse toOrganizationCompactResponse(Organization organization);
}