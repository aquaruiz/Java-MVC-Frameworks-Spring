package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such beer")
public class BeerNotFoundException extends BaseCustomException {
    public BeerNotFoundException() {
        super(404);
    }

    public BeerNotFoundException(String message) {
        super(404, message);
    }
}
