package org.aqua.trading.repository;

import java.util.UUID;
import org.aqua.trading.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {}
