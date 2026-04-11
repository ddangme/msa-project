package org.order.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.common.exception.ErrorCode;
import org.order.domain.entity.Order;
import org.order.domain.exception.OrderException;
import org.order.domain.exception.OrderNotFoundException;
import org.order.domain.repository.OrderRepository;
import org.order.global.exception.OrderErrorCode;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Order findById(UUID orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(OrderErrorCode.ORDER_NOT_FOUND));
    }
}
