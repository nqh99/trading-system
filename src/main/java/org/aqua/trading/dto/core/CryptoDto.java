package org.aqua.trading.dto.core;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CryptoDto {

  private String id;

  private String symbol;

  private Float open;

  private Float high;

  private Float low;

  private Float close;

  @JsonAlias({"bid", "bidPrice"})
  private BigDecimal bid;

  @JsonAlias({"ask", "askPrice"})
  private BigDecimal ask;

  private String status;
}
