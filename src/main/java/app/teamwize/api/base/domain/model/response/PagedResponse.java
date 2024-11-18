package app.teamwize.api.base.domain.model.response;


import jakarta.annotation.Nonnull;

import java.util.List;

public record PagedResponse<T>(
        @Nonnull
        List<T> contents,
        @Nonnull
        Integer pageNumber,
        @Nonnull
        Integer pageSize,
        @Nonnull
        Integer totalPages,
        @Nonnull
        Long totalContents) {

        public PagedResponse(List<T> contents, Integer pageNumber, Integer pageSize, Long totalContents) {
                this(contents, pageNumber, pageSize,
                        (int) Math.ceil((double) totalContents / pageSize),  // Calculate totalPages
                        totalContents);
        }
}