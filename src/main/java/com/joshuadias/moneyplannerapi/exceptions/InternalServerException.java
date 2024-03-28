package com.joshuadias.moneyplannerapi.exceptions;

import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

  public InternalServerException() {
    super();
  }

  public InternalServerException(String message) {
    super(message, null, true, false);
  }

  public InternalServerException(MessageEnum message) {
    super(message.getMessage(), null, true, false);
  }
}
