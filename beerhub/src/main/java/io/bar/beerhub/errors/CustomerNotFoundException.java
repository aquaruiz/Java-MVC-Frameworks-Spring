package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found!")
public class CustomerNotFoundException extends BaseCustomException {
    public CustomerNotFoundException() {
        super(404);
    }

    public CustomerNotFoundException(String message) {
        super(404, message);
    }
}
