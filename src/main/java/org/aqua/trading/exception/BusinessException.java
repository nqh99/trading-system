package org.aqua.trading.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
  private final HttpStatus status;
  private final String message;
  private Throwable cause;
}
