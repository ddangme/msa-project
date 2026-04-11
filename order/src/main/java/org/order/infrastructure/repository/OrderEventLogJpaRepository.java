package org.order.infrastructure.repository;

import org.order.domain.entity.OrderEventStatus;
import org.order.domain.entity.OrderEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderEventLogJpaRepository extends JpaRepository<OrderEventLog, UUID> {

    List<OrderEventLog> findByStatus(OrderEventStatus status);
}
