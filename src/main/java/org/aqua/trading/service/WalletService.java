package org.aqua.trading.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.aqua.trading.dto.core.CryptoDto;
import org.aqua.trading.dto.core.WalletDto;
import org.aqua.trading.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

  private final WalletRepository walletRepository;

  @Autowired
  public WalletService(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  public WalletDto retrieveBalanceByUserWallet(String walletId) {
    List<Object[]> balanceInfo =
        walletRepository.retrieveWalletBalanceById(UUID.fromString(walletId));

    WalletDto walletDto = new WalletDto();
    walletDto.setCryptoMap(new HashSet<>());
    for (Object[] row : balanceInfo) {
      walletDto.setId(row[0].toString());
      walletDto.setName((String) row[1]);
      walletDto.setCashBalance((BigDecimal) row[2]);

      CryptoDto cryptoDto = new CryptoDto();
      cryptoDto.setId(row[3].toString());
      cryptoDto.setSymbol((String) row[4]);
      cryptoDto.setAmount((BigDecimal) row[5]);
      cryptoDto.setPrice((BigDecimal) row[6]);

      walletDto.getCryptoMap().add(cryptoDto);
    }

    return walletDto;
  }
}
