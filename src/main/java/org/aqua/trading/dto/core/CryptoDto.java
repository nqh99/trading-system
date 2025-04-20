package org.aqua.trading.dto.core;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.math.BigDecimal;
import lombok.Data;

@Data
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
