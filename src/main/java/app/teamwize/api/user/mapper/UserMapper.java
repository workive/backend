package app.teamwize.api.user.mapper;



import app.teamwize.api.organization.mapper.OrganizationMapper;
import app.teamwize.api.user.domain.entity.User;
import app.teamwize.api.user.domain.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface UserMapper {
    UserResponse toUserResponse(User entity);

    List<UserResponse> toUserResponses(List<User> entities);

}
