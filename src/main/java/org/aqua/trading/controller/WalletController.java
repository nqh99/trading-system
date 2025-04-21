package org.aqua.trading.controller;

import jakarta.validation.constraints.NotEmpty;
import org.aqua.trading.service.WalletService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  private final WalletService walletService;

  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @PostMapping("/balance")
  public ResponseEntity<Object> retrieveBalanceByUserWallet(
      @RequestParam @NotEmpty @UUID String walletId) {
    return ResponseEntity.ok(walletService.retrieveBalanceByUserWallet(walletId));
  }
}
