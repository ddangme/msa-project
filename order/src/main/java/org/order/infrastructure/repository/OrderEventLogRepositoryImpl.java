package org.order.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.order.domain.entity.OrderEventStatus;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.exception.OrderEventException;
import org.order.domain.repository.OrderEventLogRepository;
import org.order.global.exception.OrderErrorCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderEventLogRepositoryImpl implements OrderEventLogRepository {

    private final OrderEventLogJpaRepository orderEventLogJpaRepository;

    @Override
    public OrderEventLog save(OrderEventLog orderEventLog) {
        return orderEventLogJpaRepository.save(orderEventLog);
    }

    @Override
    public List<OrderEventLog> findByStatus(OrderEventStatus status) {
        return orderEventLogJpaRepository.findByStatus(status);
    }

    @Override
    public OrderEventLog findById(UUID eventId) {
        return orderEventLogJpaRepository.findById(eventId)
                .orElseThrow(() -> new OrderEventException(OrderErrorCode.ORDER_EVENT_NOT_FOUND));
    }
}
