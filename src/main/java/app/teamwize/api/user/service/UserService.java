package app.teamwize.api.user.service;

import app.teamwize.api.base.domain.model.request.PaginationRequest;
import app.teamwize.api.user.exception.IncorrectPasswordException;
import app.teamwize.api.user.exception.UnableToDisableCurrentUserException;
import app.teamwize.api.auth.domain.AuthUserDetails;
import app.teamwize.api.organization.domain.entity.Organization;
import app.teamwize.api.team.domain.exception.TeamNotFoundException;
import app.teamwize.api.team.service.TeamService;
import app.teamwize.api.user.domain.UserRole;
import app.teamwize.api.user.domain.UserStatus;
import app.teamwize.api.user.domain.entity.User;
import app.teamwize.api.user.domain.request.AdminUserCreateRequest;
import app.teamwize.api.user.domain.request.UserCreateRequest;
import app.teamwize.api.user.domain.request.UserFilterRequest;
import app.teamwize.api.user.domain.request.UserUpdateRequest;
import app.teamwize.api.user.domain.response.UserResponse;
import app.teamwize.api.user.exception.UserAlreadyExistsException;
import app.teamwize.api.user.exception.UserNotFoundException;
import app.teamwize.api.user.mapper.UserMapper;
import app.teamwize.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static app.teamwize.api.user.repository.UserSpecifications.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamService teamService;

    public void checkIfUserExists(String email) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }

    public User getUser(long organizationId, long userId) throws UserNotFoundException {
        return getById(organizationId, userId);
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public UserDetails getUserDetails(Long id) throws UserNotFoundException {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(null));

        return AuthUserDetails.build(user);
    }

    @Transactional
    public UserResponse createOrganizationAdmin(Long organizationId,Long teamId, AdminUserCreateRequest request) throws UserAlreadyExistsException, TeamNotFoundException {
        checkIfUserExists(request.getEmail());
        var team = teamService.getTeam(organizationId,teamId);
        var user = buildUser(request.getEmail(),
                UserRole.ADMIN,
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                organizationId
        );
        user.setTeam(team);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.merge(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse createUser(Long organizationId, UserCreateRequest request)
            throws UserAlreadyExistsException {
        checkIfUserExists(request.getEmail());
        var user = buildUser(
                request.getEmail(),
                request.getRole(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                organizationId
        );
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.merge(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public User partiallyUpdateUser(Long organizationId, Long userId, UserUpdateRequest request) throws UserNotFoundException, UserAlreadyExistsException {
        var user = getById(organizationId, userId);

        if (request.getEmail() != null) {
            checkIfUserExists(request.getEmail().get());
            user.setEmail(request.getEmail().get());
        }
        if (request.getFirstName() != null) {
            request.getFirstName().ifPresent(user::setFirstName);
        }
        if (request.getLastName() != null) {
            request.getLastName().ifPresent(user::setLastName);
        }
        if (request.getPhone() != null) {
            request.getPhone().ifPresent(user::setPhone);
        }
        return userRepository.update(user);
    }


    @Transactional
    public User changeUserStatus(long organizationId, long adminUserId, long targetUserId, UserStatus status)
            throws UnableToDisableCurrentUserException, UserNotFoundException {
        if (adminUserId == targetUserId) {
            throw new UnableToDisableCurrentUserException(targetUserId);
        }

        var user = getById(organizationId, targetUserId);
        user.setStatus(status);
        return userRepository.update(user);
    }

    @Transactional
    public void changePassword(Long organizationId, Long userId, String currentPassword, String newPassword)
            throws UserNotFoundException, IncorrectPasswordException {
        var user = getById(organizationId, userId);

        checkPasswordMatch(currentPassword, user.getPassword());

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.update(user);
    }

    @Transactional
    public void resetPassword(Long organizationId, Long userId, String password) throws UserNotFoundException {
        var user = getById(organizationId, userId);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.update(user);
    }


    private User buildUser(String email, UserRole role, String firstName, String lastName, String phone,
                           Long organizationId) {
        return new User()
                .setStatus(UserStatus.ENABLED)
                .setEmail(email)
                .setRole(role)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhone(phone)
                .setOrganization(new Organization(organizationId));
    }


    private User getById(Long organizationId, Long userId) throws UserNotFoundException {
        return userRepository.findByOrganizationIdAndId(organizationId, userId)
                .orElseThrow(() -> new UserNotFoundException(organizationId, userId));
    }

    private void checkPasswordMatch(String rawPassword, String encodedPassword) throws IncorrectPasswordException {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IncorrectPasswordException();
        }
    }

    public Page<User> getUsers(Long organizationId, UserFilterRequest request, PaginationRequest page) {
        var sort = Sort.by("id").descending();
        var pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        var specs = hasOrganizationId(organizationId);

        specs = specs.and(hasNotDeleted());

        if (request.teamId() != null) {
            specs = specs.and(hasTeamId(request.teamId()));
        }
        if (request.searchTerm() != null) {
            specs = specs.and(hasNameLike(request.searchTerm()));
        }
        return userRepository.findAll(specs, pageRequest);
    }
}
