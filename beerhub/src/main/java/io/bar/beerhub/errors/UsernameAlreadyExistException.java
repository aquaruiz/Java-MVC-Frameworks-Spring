package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT, reason = "Username already exists")
public class UsernameAlreadyExistException extends BaseCustomException {
    public UsernameAlreadyExistException() {
        super(418);
    }

    public UsernameAlreadyExistException(String message) {
        super(404, message);
    }
}
