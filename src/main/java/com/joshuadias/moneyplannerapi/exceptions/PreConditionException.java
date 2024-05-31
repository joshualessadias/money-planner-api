package com.joshuadias.moneyplannerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PreConditionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PreConditionException(String message) {
        super(message, null, true, true);
    }
}
