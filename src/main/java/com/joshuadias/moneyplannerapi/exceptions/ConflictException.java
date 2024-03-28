package com.joshuadias.moneyplannerapi.exceptions;

import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message, null, true, false);
    }

    public ConflictException(MessageEnum message) {
        super(message.getMessage(), null, true, false);
    }
}
