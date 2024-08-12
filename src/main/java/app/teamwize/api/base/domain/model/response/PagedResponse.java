package app.teamwize.api.base.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nonnull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {
    @Nonnull
    List<T> contents;

    @Nonnull
    Integer pageNumber;
    @Nonnull
    Integer pageSize;
    @Nonnull
    Integer totalPages;
    @Nonnull
    Long totalContents;

}