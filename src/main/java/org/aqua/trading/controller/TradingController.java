package org.aqua.trading.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.aqua.trading.dto.core.TradeDto;
import org.aqua.trading.service.TradingService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/history")
  public ResponseEntity<Object> getTradeHistory(
      @RequestParam @UUID String userId,
      @RequestParam(required = false) @NotBlank String status,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime toDate) {
    return ResponseEntity.ok(
        tradingService.retrieveOrderHistoryByUser(userId, status, fromDate, toDate));
  }
}
