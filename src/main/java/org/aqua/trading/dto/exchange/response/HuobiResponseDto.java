package org.aqua.trading.dto.exchange.response;

import java.util.List;
import lombok.Data;
import org.aqua.trading.dto.core.CryptoDto;

@Data
public class HuobiResponseDto {
  private List<CryptoDto> data;
  private String status;
  private long ts;
}
