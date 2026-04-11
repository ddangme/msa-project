package org.order.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.entity.OrderEventStatus;
import org.order.domain.repository.OrderEventLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderEventProcessor {

    private final OrderEventLogRepository orderEventLogRepository;

    @Transactional
    public List<OrderEventLog> getTargetEventsAndMarkPublishing() {
        List<OrderEventLog> events = orderEventLogRepository.findByStatus(OrderEventStatus.INIT);
        events.forEach(OrderEventLog::markAsPublishing);
        return events;
    }

    @Transactional
    public void processSuccess(UUID eventId) {
        OrderEventLog event = orderEventLogRepository.findById(eventId);
        event.markAsPublish();
    }

    @Transactional
    public void processFailure(UUID eventId, int maxRetry) {
        OrderEventLog event = orderEventLogRepository.findById(eventId);
        event.increaseRetryCount(maxRetry);
    }
}
