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
@Table(name = "wallet")
public class Wallet {

  @Id
  @Column(nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column private String name;

  @Column(name = "cash_balance", precision = 20, scale = 3)
  @ToString.Include
  private BigDecimal cashBalance = BigDecimal.ZERO;

  @Column(nullable = false)
  @EqualsAndHashCode.Include
  private int priority;

  @Column(length = 30, nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
