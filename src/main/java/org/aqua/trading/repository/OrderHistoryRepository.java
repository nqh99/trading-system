package org.aqua.trading.repository;

import java.util.UUID;
import org.aqua.trading.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderHistoryRepository
    extends JpaRepository<OrderHistory, UUID>, JpaSpecificationExecutor<OrderHistory> {}
