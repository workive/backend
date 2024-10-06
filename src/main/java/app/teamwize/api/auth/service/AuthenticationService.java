package app.teamwize.api.auth.service;


import app.teamwize.api.auth.domain.request.LoginRequest;
import app.teamwize.api.base.exception.BaseException;
import app.teamwize.api.leave.exception.LeaveTypeNotFoundException;
import app.teamwize.api.leave.service.LeavePolicyService;
import app.teamwize.api.organization.service.OrganizationService;
import app.teamwize.api.user.domain.request.AdminUserCreateRequest;
import app.teamwize.api.user.exception.UserAlreadyExistsException;
import app.teamwize.api.user.exception.UserNotFoundException;
import app.teamwize.api.user.mapper.UserMapper;
import app.teamwize.api.user.service.UserService;
import app.teamwize.api.auth.domain.request.RegistrationRequest;
import app.teamwize.api.auth.domain.response.AuthenticationResponse;
import app.teamwize.api.auth.exception.InvalidCredentialException;
import app.teamwize.api.organization.domain.event.OrganizationCreateRequest;
import app.teamwize.api.organization.domain.event.OrganizationCreatedEvent;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.team.domain.exception.TeamNotFoundException;
import app.teamwize.api.team.domain.request.TeamCreateRequest;
import app.teamwize.api.team.service.TeamService;
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
    private final LeavePolicyService leavePolicyService;


    @Transactional(rollbackFor = BaseException.class)
    public AuthenticationResponse register(RegistrationRequest request) throws UserAlreadyExistsException, OrganizationNotFoundException, TeamNotFoundException, LeaveTypeNotFoundException {
        var organization = organizationService.registerOrganization(new OrganizationCreateRequest(request.organizationName(), request.country(), request.timezone()));
        var leavePolicy = leavePolicyService.createDefaultLeavePolicy(organization.getId());
        var team = teamService.createTeam(organization.getId(), new TeamCreateRequest("Default", null));
        var registerRequest = new AdminUserCreateRequest(
                request.email(),
                request.password(),
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.timezone(),
                request.country(),
                leavePolicy.getId()
        );

        var user = userService.createOrganizationAdmin(organization.getId(), team.getId(), registerRequest);
        eventPublisher.publishEvent(new OrganizationCreatedEvent(organization, userMapper.toUserResponse(user)));


        var accessToken = tokenService.generateAccessToken(
                user.getId().toString(),
                organization.getId(),
                user.getId(),
                user.getRole(),
                LocalDateTime.now().plusDays(1)
        );
        var refreshToken = tokenService.generateRefreshToken(user.getId().toString(), LocalDateTime.now().plusDays(7));
        return new AuthenticationResponse(accessToken, refreshToken, userMapper.toUserResponse(user));
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
