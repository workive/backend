package app.teamwize.api.auth.model;

import app.teamwize.api.user.domain.UserRole;
import app.teamwize.api.user.domain.UserStatus;
import app.teamwize.api.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AuthUserDetails implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";

    private Long id;
    private String email;
    private String password;
    private UserStatus status;
    private Long siteId;
    private Long organizationId;
    private UserRole role;
    private String apiKey;

    public AuthUserDetails(Long id,
                           String email,
                           String password,
                           UserStatus status,
                           Long organizationId,
                           UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.organizationId = organizationId;
        this.role = role;
    }

    public AuthUserDetails(Long id,
                           String apiKey,
                           String email,
                           String password,
                           UserStatus status,
                           Long organizationId,
                           UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.siteId = siteId;
        this.organizationId = organizationId;
        this.apiKey = apiKey;
        this.role = role;
    }

    public static AuthUserDetails build(Long userId, UserRole role) {
        return new AuthUserDetails(userId, null, null, UserStatus.ENABLED, null, role);
    }

    public static AuthUserDetails build(Long userId, Long organizationId,  UserRole role) {
        return new AuthUserDetails(userId, null, null, UserStatus.ENABLED, organizationId, role);
    }

    public static AuthUserDetails build(String apiKey, Long organizationId, UserRole role) {
        return new AuthUserDetails(null, apiKey, null, null, UserStatus.ENABLED, organizationId, role);
    }


    public static AuthUserDetails build(User user) {
        return new AuthUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                user.getOrganization() == null ? null : user.getOrganization().getId(),
                user.getRole());
    }

    public Long getId() {
        return id;
    }



    public String getApiKey() {
        return apiKey;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        if (id != null) {
            return Long.toString(id);
        } else {
            return apiKey;
        }
    }


    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ENABLED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()));
    }

    public UserRole getRole() {
        return role;
    }

}