package org.aqua.trading.config.exchange;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "exchange.binance")
@Getter
@Setter
public class BinanceConfig {
  private String tickerUrl;
}
