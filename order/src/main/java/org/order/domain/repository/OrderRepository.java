package org.order.domain.repository;

import org.order.domain.entity.Order;

public interface OrderRepository {

    Order save(Order order);
}
