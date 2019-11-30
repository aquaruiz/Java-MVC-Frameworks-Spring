package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such beer")
public class BeerNotFoundException extends RuntimeException {
    public BeerNotFoundException(String message) {
        super(message);
    }
}
