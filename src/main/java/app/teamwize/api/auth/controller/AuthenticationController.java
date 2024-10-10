package app.teamwize.api.auth.controller;


import app.teamwize.api.auth.domain.request.LoginRequest;
import app.teamwize.api.auth.service.AuthenticationService;
import app.teamwize.api.leave.exception.LeaveTypeNotFoundException;
import app.teamwize.api.organization.exception.OrganizationNotFoundException;
import app.teamwize.api.team.domain.exception.TeamNotFoundException;
import app.teamwize.api.user.exception.UserAlreadyExistsException;
import app.teamwize.api.auth.domain.request.RegistrationRequest;
import app.teamwize.api.auth.domain.response.AuthenticationResponse;
import app.teamwize.api.auth.exception.InvalidCredentialException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@Valid @RequestBody RegistrationRequest request)
            throws UserAlreadyExistsException, OrganizationNotFoundException, TeamNotFoundException, LeaveTypeNotFoundException {
        return authenticationService.register(request);
    }


    @PostMapping("login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest request) throws InvalidCredentialException {
        return authenticationService.login(request);
    }


}
