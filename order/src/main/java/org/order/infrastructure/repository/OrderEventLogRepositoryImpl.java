package org.order.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.order.domain.entity.EventStatus;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.repository.OrderEventLogRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderEventLogRepositoryImpl implements OrderEventLogRepository {

    private final OrderEventLogJpaRepository orderEventLogJpaRepository;

    @Override
    public OrderEventLog save(OrderEventLog orderEventLog) {
        return orderEventLogJpaRepository.save(orderEventLog);
    }

    @Override
    public List<OrderEventLog> findByStatus(EventStatus status) {
        return orderEventLogJpaRepository.findByStatus(status);
    }
}
