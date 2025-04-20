package org.aqua.trading.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import java.text.MessageFormat;
import org.aqua.trading.dto.common.ExceptionDto;
import org.aqua.trading.dto.core.CryptoDto;
import org.aqua.trading.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {
  private final CryptoService cryptoService;

  @Autowired
  public CryptoController(CryptoService cryptoService) {
    this.cryptoService = cryptoService;
  }

  @GetMapping("/search")
  public ResponseEntity<Object> getAggregatedPrice(
      @RequestParam @NotEmpty String symbol, HttpServletRequest request) {
    CryptoDto crypto = cryptoService.findBestAggregatedPrice(symbol);

    if (crypto == null) {
      ExceptionDto errorResponse =
          new ExceptionDto(
              HttpStatus.NOT_FOUND.getReasonPhrase(),
              request.getServletPath(),
              MessageFormat.format("No cryptocurrency found with symbol: {0}", symbol));
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    return ResponseEntity.ok(crypto);
  }
}
