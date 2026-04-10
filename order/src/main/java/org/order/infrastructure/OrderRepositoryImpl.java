package org.order.infrastructure;

import lombok.RequiredArgsConstructor;
import org.order.domain.entity.Order;
import org.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }
}
