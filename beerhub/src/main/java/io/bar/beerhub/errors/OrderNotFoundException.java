package io.bar.beerhub.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order not found!")
public class OrderNotFoundException extends BaseCustomException {
    public OrderNotFoundException() {
        super(404);
    }

    public OrderNotFoundException(String message) {
        super(404, message);
    }
}
