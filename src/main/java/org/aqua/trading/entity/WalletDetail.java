package org.aqua.trading.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "wallet_detail")
@IdClass(WalletDetail.WalletDetailId.class)
public class WalletDetail {

  @Id
  @Column(name = "wallet_id", nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private UUID walletId;

  @Id
  @Column(name = "crypto_id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID cryptoId;

  @Column(precision = 15, scale = 3)
  private BigDecimal amount = BigDecimal.ZERO;

  @Column(length = 30, nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Data
  public static class WalletDetailId {
    private UUID walletId;
    private UUID cryptoId;
  }
}
