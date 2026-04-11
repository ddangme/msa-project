package org.order.domain.repository;

import org.order.domain.entity.Order;

import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Order findById(UUID orderId);
}
