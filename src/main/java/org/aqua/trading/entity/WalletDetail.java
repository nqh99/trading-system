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
public class WalletDetail {

  @Id
  @Column(nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wallet_id", nullable = false)
  private Wallet wallet;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "crypto_id", nullable = false)
  private Crypto crypto;

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
}
