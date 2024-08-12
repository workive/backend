package app.teamwize.api.base.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "USER_ALREADY_EXISTS")
@RequiredArgsConstructor
public class UserAlreadyExistsException extends BaseException {


    private final Integer pageNumber;

}
