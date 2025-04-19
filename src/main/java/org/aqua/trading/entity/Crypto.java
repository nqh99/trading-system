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
@Entity
@Table(name = "crypto")
public class Crypto {

  @Id @NonNull @EqualsAndHashCode.Include private UUID id;

  @NonNull @EqualsAndHashCode.Include private String symbol;

  @Column private BigDecimal open;

  @Column private BigDecimal high;

  @Column private BigDecimal low;

  @Column private BigDecimal close;

  @Column private BigDecimal bid;

  @Column private BigDecimal ask;

  @Column @NonNull @EqualsAndHashCode.Include private String status;

  @Column private LocalDateTime createdAt;

  @Column private LocalDateTime updatedAt;
}
