package org.order.domain.repository;

import org.order.domain.entity.EventStatus;
import org.order.domain.entity.OrderEventLog;

import java.util.List;

public interface OrderEventLogRepository {

    OrderEventLog save(OrderEventLog orderEventLog);

    List<OrderEventLog> findByStatus(EventStatus eventStatus);
}
