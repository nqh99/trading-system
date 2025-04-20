package org.aqua.trading.controller;

import jakarta.validation.Valid;
import org.aqua.trading.dto.core.TradeDto;
import org.aqua.trading.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradingController {

  private final TradingService tradingService;

  @Autowired
  public TradingController(TradingService tradingService) {
    this.tradingService = tradingService;
  }

  @PostMapping("/new-trade")
  public ResponseEntity<Object> trade(@RequestBody @Valid TradeDto request) {
    tradingService.tradeBaseOnBestAggregatedPrice(request);
    return ResponseEntity.ok("Trade successful");
  }
}
