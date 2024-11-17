package app.teamwize.api.base.domain.model;

import jakarta.annotation.Nonnull;
import lombok.With;

import java.util.List;

@With
public record Paged<T>(
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


        public Paged(List<T> contents, Integer pageNumber, Integer pageSize, Long totalContents) {
                this(contents, pageNumber, pageSize,
                        (int) Math.ceil((double) totalContents / pageSize),  // Calculate totalPages
                        totalContents);
        }
}