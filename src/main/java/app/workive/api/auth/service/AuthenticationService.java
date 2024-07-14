package app.workive.api.auth.service;


import app.workive.api.auth.domain.request.LoginRequest;
import app.workive.api.auth.domain.request.RegistrationRequest;
import app.workive.api.auth.domain.response.AuthenticationResponse;
import app.workive.api.auth.exception.InvalidCredentialException;
import app.workive.api.base.exception.BaseException;
import app.workive.api.organization.domain.event.OrganizationCreateRequest;
import app.workive.api.organization.domain.event.OrganizationCreatedEvent;
import app.workive.api.organization.exception.OrganizationNotFoundException;
import app.workive.api.organization.service.OrganizationService;
import app.workive.api.team.domain.exception.TeamNotFoundException;
import app.workive.api.team.domain.request.TeamCreateRequest;
import app.workive.api.team.service.TeamService;
import app.workive.api.user.domain.request.AdminUserCreateRequest;
import app.workive.api.user.exception.UserAlreadyExistsException;
import app.workive.api.user.exception.UserNotFoundException;
import app.workive.api.user.mapper.UserMapper;
import app.workive.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final OrganizationService organizationService;
    private final TokenService tokenService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;
    private final TeamService teamService;


    @Transactional(rollbackFor = BaseException.class)
    public AuthenticationResponse register(RegistrationRequest request) throws UserAlreadyExistsException, OrganizationNotFoundException, TeamNotFoundException {
        var organization = organizationService.registerOrganization(new OrganizationCreateRequest(request.getOrganizationName(),request.getCountryCode(), request.getTimezone()));
        var team = teamService.createTeam(organization.getId(), new TeamCreateRequest("Default", null));
        var registerRequest = new AdminUserCreateRequest(request.getEmail(), request.getPassword(),
                request.getFirstName(), request.getLastName(), request.getPhone());
        var user = userService.createOrganizationAdmin(organization.getId(), team.getId(),registerRequest);
        eventPublisher.publishEvent(new OrganizationCreatedEvent(organization, user));

        var accessToken = tokenService.generateAccessToken(
                user.getId().toString(),
                organization.getId(),
                user.getId(),
                user.getRole(),
                LocalDateTime.now().plusDays(1)
        );
        var refreshToken = tokenService.generateRefreshToken(user.getId().toString(), LocalDateTime.now().plusDays(7));
        return new AuthenticationResponse(accessToken, refreshToken, user);
    }

    public AuthenticationResponse login(LoginRequest request) throws InvalidCredentialException {
        try {
            var user = userService.getUserByEmail(request.getEmail());
            var accessToken = tokenService.generateAccessToken(
                    user.getId().toString(),
                    user.getOrganization().getId(),
                    user.getId(),
                    user.getRole(),
                    LocalDateTime.now().plusDays(1)
            );
            var refreshToken = tokenService.generateRefreshToken(user.getId().toString(), LocalDateTime.now().plusDays(7));
            return new AuthenticationResponse(accessToken, refreshToken, userMapper.toUserResponse(user));

        } catch (UserNotFoundException ex) {
            throw new InvalidCredentialException(request.getEmail());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            return userService.getUserDetails(Long.valueOf(userId));
        } catch (UserNotFoundException ex) {
            throw new UsernameNotFoundException(userId);
        }
    }

//    public ApiKeyValidateResponse validateApiKey(ApiKeyValidateRequest request) throws ApiKeyNotFoundException {
//        var apiKey = apiKeyService.findByKey(request.getApiKey());
//        var site = siteService.getOrganizationDefaultSite(apiKey.getOrganizationId());
//
//        return new ApiKeyValidateResponse()
//                .setOrganizationId(apiKey.getOrganizationId())
//                .setSiteId(site.getId());
//    }

}
