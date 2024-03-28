package com.joshuadias.moneyplannerapi.exceptions;

import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class DefaultException extends RuntimeException {

    public DefaultException() {
        super();
    }

    public DefaultException(String message) {
        super(message, null, true, false);
    }

    public DefaultException(MessageEnum message) {
        super(message.getMessage(), null, true, false);
    }
}
