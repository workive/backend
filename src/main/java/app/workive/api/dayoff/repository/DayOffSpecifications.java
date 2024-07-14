package app.workive.api.dayoff.repository;


import app.workive.api.dayoff.domain.DayOffStatus;
import app.workive.api.dayoff.domain.entity.DayOff;
import app.workive.api.user.domain.UserStatus;
import app.workive.api.user.domain.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class DayOffSpecifications {

    public static Specification<DayOff> hasOrganizationId(Long organizationId) {
        return (root, query, builder) -> builder.equal(root.get("organization").get("id"), organizationId);
    }

    public static Specification<DayOff> hasTeamId(Long teamId) {
        return (root, query, builder) -> {
            Join<DayOff, User> userJoin = root.join("user");
           return builder.equal(userJoin.get("team").get("id"), teamId);
        };
    }

    public static Specification<DayOff> hasUserId(Long userId) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<DayOff> hasStatus(DayOffStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

}

