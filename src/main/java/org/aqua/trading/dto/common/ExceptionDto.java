package org.aqua.trading.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonInclude(Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionDto {
  private final String status;
  private final String path;
  private final String message;
  private final Instant timestamp = Instant.now();
  private Object detail;
}
