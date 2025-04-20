package org.aqua.trading.dto.core;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
public class TradeDto {
  @NotEmpty @UUID private String userId;

  @NotEmpty @UUID private String cryptoId;

  @NotNull
  @Pattern(regexp = "^[BbSs]$", message = "must be either 'B' or 'S', case insensitive")
  private String bs;

  @PositiveOrZero private BigDecimal amount;

  @PositiveOrZero private BigDecimal price;
}
