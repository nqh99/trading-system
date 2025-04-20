package org.aqua.trading.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
  private final HttpStatus status;
  private final Object detail;

  public BusinessException(HttpStatus status, String message) {
    super(message);
    this.status = status;
    this.detail = null;
  }

  public BusinessException(HttpStatus status, String message, Object detail) {
    super(message);
    this.status = status;
    this.detail = detail;
  }
}
