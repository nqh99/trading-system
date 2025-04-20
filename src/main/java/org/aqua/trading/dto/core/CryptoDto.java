package org.aqua.trading.dto.core;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(Include.NON_NULL)
public class CryptoDto {

  @EqualsAndHashCode.Include private String id;

  private String symbol;

  private BigDecimal price;

  private BigDecimal amount;

  private Float open;

  private Float high;

  private Float low;

  private Float close;

  @JsonAlias({"bid", "bidPrice"})
  private BigDecimal bid;

  @JsonAlias({"bidSize", "bidQty"})
  private BigDecimal bidQty;

  @JsonAlias({"ask", "askPrice"})
  private BigDecimal ask;

  @JsonAlias({"askSize", "askQty"})
  private BigDecimal askQty;

  @EqualsAndHashCode.Include private String status;
}
