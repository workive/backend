package app.teamwize.api.user.repository;


import app.teamwize.api.user.domain.UserStatus;
import app.teamwize.api.user.domain.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> hasOrganizationId(Long organizationId) {
        return (root, query, builder) -> builder.equal(root.get("organization").get("id"), organizationId);
    }

    public static Specification<User> hasTeamId(Long teamId) {
        return (root, query, builder) -> builder.equal(root.get("team").get("id"), teamId);
    }


    public static Specification<User> hasStatus(UserStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    public static Specification<User> hasNameLike(String name) {
        return (root, query, builder) -> builder.like(builder.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

    public static Specification<User> hasNotDeleted() {
        return (root, query, builder) -> builder.notEqual(root.get("status"), UserStatus.ARCHIVED);
    }
}

