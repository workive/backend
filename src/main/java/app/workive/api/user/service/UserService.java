package app.workive.api.user.service;

import app.workive.api.auth.domain.AuthUserDetails;
import app.workive.api.organization.domain.entity.Organization;
import app.workive.api.user.domain.UserRole;
import app.workive.api.user.domain.UserStatus;
import app.workive.api.user.domain.entity.User;
import app.workive.api.user.domain.request.AdminUserCreateRequest;
import app.workive.api.user.domain.request.UserCreateRequest;
import app.workive.api.user.domain.request.UserUpdateRequest;
import app.workive.api.user.domain.response.UserResponse;
import app.workive.api.user.exception.IncorrectPasswordException;
import app.workive.api.user.exception.UnableToDisableCurrentUserException;
import app.workive.api.user.exception.UserAlreadyExistsException;
import app.workive.api.user.exception.UserNotFoundException;
import app.workive.api.user.mapper.UserMapper;
import app.workive.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    public UserResponse createOrganizationAdmin(Long organizationId, AdminUserCreateRequest request) throws UserAlreadyExistsException {
        checkIfUserExists(request.getEmail());
        var user = buildUser(request.getEmail(),
                UserRole.ORGANIZATION_ADMIN,
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
        userRepository.update(user);
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
}
