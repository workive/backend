package app.workive.api.auth.controller;



import app.workive.api.auth.domain.request.LoginRequest;
import app.workive.api.auth.domain.request.RegistrationRequest;
import app.workive.api.auth.domain.response.AuthenticationResponse;
import app.workive.api.auth.exception.InvalidCredentialException;
import app.workive.api.auth.service.AuthenticationService;
import app.workive.api.organization.exception.OrganizationNotFoundException;
import app.workive.api.team.domain.exception.TeamNotFoundException;
import app.workive.api.user.exception.UserAlreadyExistsException;
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
    public AuthenticationResponse register(@Valid @RequestBody RegistrationRequest request) throws UserAlreadyExistsException, OrganizationNotFoundException, TeamNotFoundException {
        return authenticationService.register(request);
    }


    @PostMapping("login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest request) throws InvalidCredentialException {
        return authenticationService.login(request);
    }



}
