package org.order.domain.repository;

import org.order.domain.entity.OrderEventStatus;
import org.order.domain.entity.OrderEventLog;

import java.util.List;
import java.util.UUID;

public interface OrderEventLogRepository {

    OrderEventLog save(OrderEventLog orderEventLog);

    List<OrderEventLog> findByStatus(OrderEventStatus orderEventStatus);

    OrderEventLog findById(UUID eventId);
}
