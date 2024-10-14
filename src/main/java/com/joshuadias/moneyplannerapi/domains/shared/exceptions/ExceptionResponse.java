package com.joshuadias.moneyplannerapi.domains.shared.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExceptionResponse extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;
  private final Date timestamp;
  private final String message;
  private final List<String> details;

  public ExceptionResponse(String message, List<String> details) {
    super(message, null, true, false);
    this.message = message;
    this.details = details;
    timestamp = new Date();
  }

  public ExceptionResponse(Date timestamp, String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
    this.timestamp = timestamp;
  }
}
