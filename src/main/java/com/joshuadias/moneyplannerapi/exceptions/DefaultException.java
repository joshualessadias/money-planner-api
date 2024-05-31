package com.joshuadias.moneyplannerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class DefaultException extends RuntimeException {

    public DefaultException(String message) {
        super(message, null, true, true);
    }
}
