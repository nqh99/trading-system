package org.aqua.trading.repository;

import java.util.UUID;
import org.aqua.trading.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<Order, UUID> {}
