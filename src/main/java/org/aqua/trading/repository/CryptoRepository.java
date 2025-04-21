package org.aqua.trading.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.aqua.trading.entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, UUID> {
  List<Crypto> findAllByStatus(String status);

  Crypto findBySymbolAndStatus(String symbol, String status);

  Optional<Crypto> findByIdAndStatus(@NonNull UUID id, @NonNull String status);
}
