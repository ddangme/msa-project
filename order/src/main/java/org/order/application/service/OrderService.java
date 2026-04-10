package org.order.application.service;

import lombok.RequiredArgsConstructor;
import org.order.application.dto.CreateOrderCommand;
import org.order.domain.entity.Order;
import org.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public UUID createOrder(CreateOrderCommand command) {
        // TODO: product 도메인 연동 로직으로 수정 예정
        Long totalPrice = 10_000L;
        Order order = command.toEntity(totalPrice);
        return orderRepository.save(order).getOrderId();
    }
}
