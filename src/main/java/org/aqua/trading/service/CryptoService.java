package org.aqua.trading.service;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.aqua.trading.component.factory.RestClientFactory;
import org.aqua.trading.config.exchange.BinanceConfig;
import org.aqua.trading.config.exchange.HuobiConfig;
import org.aqua.trading.dto.core.CryptoDto;
import org.aqua.trading.dto.exchange.response.HuobiResponseDto;
import org.aqua.trading.entity.Crypto;
import org.aqua.trading.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class CryptoService {
  private final RestClientFactory restClientFactory;
  private final CryptoRepository cryptoRepository;
  private final HuobiConfig huobiConfig;
  private final BinanceConfig binanceConfig;

  @Autowired
  public CryptoService(
      RestClientFactory restClientFactory,
      CryptoRepository cryptoRepository,
      HuobiConfig huobiConfig,
      BinanceConfig binanceConfig) {
    this.restClientFactory = restClientFactory;
    this.cryptoRepository = cryptoRepository;
    this.huobiConfig = huobiConfig;
    this.binanceConfig = binanceConfig;
  }

  @Scheduled(fixedDelay = 10000)
  public void aggregatePrices() {
    List<Crypto> cryptoList = cryptoRepository.findAllByStatus("N");

    if (cryptoList.isEmpty()) {
      log.warn("All cryptocurrencies are already delisted");
      return;
    }

    HuobiResponseDto huobiResponse =
        fetchDataFromExchange(huobiConfig.getTickerUrl(), new ParameterizedTypeReference<>() {});

    List<CryptoDto> binanceResponse =
        fetchDataFromExchange(binanceConfig.getTickerUrl(), new ParameterizedTypeReference<>() {});

    Set<Crypto> cryptoChangedSet = new HashSet<>();

    for (Crypto crypto : cryptoList) {
      String symbol = crypto.getSymbol();

      CryptoDto cryptoDataFromBinance =
          Optional.ofNullable(binanceResponse).orElseGet(ArrayList::new).stream()
              .filter(data -> symbol.equalsIgnoreCase(data.getSymbol()))
              .findFirst()
              .orElse(new CryptoDto());

      if (cryptoDataFromBinance.getBid() == null) {
        log.warn("No Bid price found for symbol from Binance exchange: {}", symbol);
      }

      if (cryptoDataFromBinance.getAsk() == null) {
        log.warn("No Ask price found for symbol from Binance exchange: {}", symbol);
      }

      CryptoDto cryptoDataFromHuobi =
          Optional.ofNullable(
                  Optional.ofNullable(huobiResponse).orElseGet(HuobiResponseDto::new).getData())
              .orElseGet(ArrayList::new)
              .stream()
              .filter(data -> symbol.equalsIgnoreCase(data.getSymbol()))
              .findFirst()
              .orElse(new CryptoDto());

      if (cryptoDataFromHuobi.getBid() == null) {
        log.warn("No Bid price found for symbol from Huobi exchange: {}", symbol);
      }

      if (cryptoDataFromHuobi.getAsk() == null) {
        log.warn("No Ask price found for symbol from Huobi exchange: {}", symbol);
      }

      BigDecimal bidPrice =
          Stream.of(
                  Optional.ofNullable(cryptoDataFromBinance.getBid()),
                  Optional.ofNullable(cryptoDataFromHuobi.getBid()))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .min(BigDecimal::compareTo)
              .orElse(crypto.getBid());

      BigDecimal askPrice =
          Stream.of(
                  Optional.ofNullable(cryptoDataFromBinance.getAsk()),
                  Optional.ofNullable(cryptoDataFromHuobi.getAsk()))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .max(BigDecimal::compareTo)
              .orElse(crypto.getAsk());

      boolean needUpdate = false;
      if (!bidPrice.equals(crypto.getBid())) {
        log.info("Update Bid price {} for symbol: {}", bidPrice, symbol);
        crypto.setBid(bidPrice);
        needUpdate = true;
      }

      if (!askPrice.equals(crypto.getAsk())) {
        log.info("Update Ask price {} for symbol: {}", askPrice, symbol);
        crypto.setAsk(askPrice);
        needUpdate = true;
      }

      if (needUpdate) {
        crypto.setUpdatedAt(LocalDateTime.now());
        cryptoChangedSet.add(crypto);
      }
    }

    cryptoRepository.saveAll(cryptoChangedSet);
  }

  private <T> T fetchDataFromExchange(String url, ParameterizedTypeReference<T> responseType) {
    RestClient client = restClientFactory.createRestClient();

    T response;
    try {
      response = client.get().uri(URI.create(url)).retrieve().toEntity(responseType).getBody();
    } catch (Exception e) {
      log.warn("Error fetching url: {}", url);
      log.warn("Error fetching data from exchange", e);
      return null;
    }

    return response;
  }
}
