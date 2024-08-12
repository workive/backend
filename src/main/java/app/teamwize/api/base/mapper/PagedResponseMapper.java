package app.teamwize.api.base.mapper;

import app.teamwize.api.base.domain.model.response.PagedResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagedResponseMapper {

   default <T> PagedResponse<T> toPagedResponse(List<T> contents, Integer pageNumber, Integer pageSize, Integer totalPages, Long totalContents) {
        return new PagedResponse<>(contents,pageNumber,pageSize,totalPages,totalContents);
    }

}
