package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Waitress not found!")
public class WaitressNotFoundException extends BaseCustomException {
    public WaitressNotFoundException() {
        super(404);
    }

    public WaitressNotFoundException(String message) {
        super(404, message);
    }
}
