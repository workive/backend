package app.teamwize.api.auth.service;

import app.teamwize.api.user.domain.UserRole;
import app.teamwize.api.auth.model.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    public Long getUserOrganizationId() {
        return this.getUserDetails().getOrganizationId();
    }



    public Long getUserId() {
        return this.getUserDetails().getId();
    }

    public UserRole getUserRole() {
        return this.getUserDetails().getRole();
    }

    private AuthUserDetails getUserDetails() {
        return (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public UserRole getAuthenticatedUserRole() {
        return getUserDetails().getRole();
    }
}

