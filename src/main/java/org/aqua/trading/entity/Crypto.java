package org.aqua.trading.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "crypto")
public class Crypto {

  @Id @NonNull @EqualsAndHashCode.Include private UUID id;

  @NonNull @EqualsAndHashCode.Include private String symbol;

  @Column private BigDecimal price;

  @Column private BigDecimal amount;

  @Column private BigDecimal open;

  @Column private BigDecimal high;

  @Column private BigDecimal low;

  @Column private BigDecimal close;

  @Column private BigDecimal bid;

  @Column(name = "bid_qty", precision = 15, scale = 7)
  private BigDecimal bidQty;

  @Column private BigDecimal ask;

  @Column(name = "ask_qty", precision = 15, scale = 7)
  private BigDecimal askQty;

  @Column @NonNull @EqualsAndHashCode.Include private String status;

  @Column @ToString.Exclude private LocalDateTime createdAt;

  @Column @ToString.Exclude private LocalDateTime updatedAt;
}
