package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User Registration failed!")
public class UserRegistrationException extends RuntimeException {
    private int statusCode;

    public UserRegistrationException() {
        this.statusCode = 400;
    }
    public UserRegistrationException(String message) {
        super(message);
        this.statusCode = 400;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
