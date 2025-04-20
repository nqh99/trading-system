package org.aqua.trading.repository;

import java.util.Optional;
import java.util.UUID;
import org.aqua.trading.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

  Optional<Wallet> findSpotWalletByUserIdAndPriorityAndStatus(
      UUID userID, int priority, String status);
}
