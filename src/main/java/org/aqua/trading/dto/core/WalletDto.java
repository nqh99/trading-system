package org.aqua.trading.dto.core;

import java.math.BigDecimal;
import java.util.HashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WalletDto {

  @EqualsAndHashCode.Include private String id;

  private String name;

  private BigDecimal cashBalance;

  private HashMap<String, CryptoDto> cryptoMap;
}
