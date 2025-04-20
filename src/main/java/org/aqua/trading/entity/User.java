package org.aqua.trading.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  @Column(nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private UUID id;

  @ToString.Include
  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private String email;

  @Column(nullable = false, unique = true)
  @EqualsAndHashCode.Include
  private String phone;

  @Column private String address;

  @ToString.Include @Column private BigDecimal balance;

  @Column(length = 30, nullable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
