package org.aqua.trading.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.aqua.trading.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

  Optional<Wallet> findSpotWalletByUserIdAndPriorityAndStatus(
      UUID userID, int priority, String status);

  @Query(
      "SELECT w.id, w.name, w.cashBalance, wd.cryptoId, c.symbol, wd.amount, wd.cryptoId FROM Wallet w LEFT JOIN WalletDetail wd ON w.id = wd.walletId LEFT JOIN Crypto c ON wd.cryptoId = c.id WHERE w.id = :walletId")
  List<Object[]> retrieveWalletBalanceById(UUID walletId);
}
