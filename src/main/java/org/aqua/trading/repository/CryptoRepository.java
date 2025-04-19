package org.aqua.trading.repository;

import java.util.List;
import java.util.UUID;
import org.aqua.trading.entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, UUID> {
  List<Crypto> findAllByStatus(String status);
}
