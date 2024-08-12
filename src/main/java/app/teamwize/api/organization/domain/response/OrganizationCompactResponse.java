package app.teamwize.api.organization.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nonnull;

@Data
@NoArgsConstructor
public class OrganizationCompactResponse {
    @Nonnull
    private Long id;
    @Nonnull
    private String name;
}
