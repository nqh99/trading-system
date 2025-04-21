package org.aqua.trading.repository;

import java.util.List;
import java.util.UUID;
import org.aqua.trading.entity.WalletDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletDetailRepository extends JpaRepository<WalletDetail, UUID> {

  List<WalletDetail> findAllByWalletId(UUID walletId);
}
