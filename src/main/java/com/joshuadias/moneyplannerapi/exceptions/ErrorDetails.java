package com.joshuadias.moneyplannerapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
  private final LocalDateTime timestamp;
  private final String message;
  private final List<String> details;
}
