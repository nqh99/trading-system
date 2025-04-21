package org.aqua.trading.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aqua.trading.dto.common.ExceptionDto;
import org.aqua.trading.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ExceptionDto> handleException(
          Throwable throwable, HttpServletRequest request) {
    log.error("Unhandled exception", throwable);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                    new ExceptionDto(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            request.getServletPath(),
                            "Internal server error"));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionDto> handleBusinessException(
      BusinessException exception, HttpServletRequest request) {
    logError(exception.getMessage(), request);

    return ResponseEntity.status(exception.getStatus())
        .body(
            new ExceptionDto(
                exception.getStatus().getReasonPhrase(),
                request.getServletPath(),
                exception.getMessage(),
                exception.getDetail()));
  }

  private void logError(String msg, HttpServletRequest request) {
    log.error(
        "Error: {} | URI: {} | Method: {} | Client IP: {} | User-Agent: {}",
        msg,
        request.getServletPath(),
        request.getMethod(),
        request.getRemoteAddr(),
        request.getHeader("User-Agent"));
  }
}
