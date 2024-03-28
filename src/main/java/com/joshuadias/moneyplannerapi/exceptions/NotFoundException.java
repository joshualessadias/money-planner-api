package com.joshuadias.moneyplannerapi.exceptions;

import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message, null, true, false);
    }

    public NotFoundException(MessageEnum message) {
        super(message.getMessage(), null, true, false);
    }
}
