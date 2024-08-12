package app.teamwize.api.base.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import org.springdoc.core.annotations.ParameterObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ParameterObject
public class PaginationRequest {
    @Nonnull
    @Min(value = 0, message = "pagination.page.invalid")
    Integer pageNumber = 0;
    @Nonnull
    @Range(min = 1, max = 100, message = "pagination.size.invalid")
    Integer pageSize = 10;
}
