package org.product.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import org.product.domain.entity.ProductEventLog;
import org.product.domain.entity.ProductEventStatus;
import org.product.domain.repository.ProductEventLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductEventProcessor {

    private final ProductEventLogRepository productEventLogRepository;

    @Transactional
    public List<ProductEventLog> getTargetEventsAndMarkPublishing() {
        List<ProductEventLog> events = productEventLogRepository.findByStatus(ProductEventStatus.INIT);
        events.forEach(ProductEventLog::markAsPublishing);
        return events;
    }

    @Transactional
    public void processSuccess(UUID eventId) {
        ProductEventLog event = productEventLogRepository.findById(eventId);
        event.markAsPublish();
    }

    @Transactional
    public void processFailure(UUID eventId, int maxRetry) {
        ProductEventLog event = productEventLogRepository.findById(eventId);
        event.increaseRetryCount(maxRetry);
    }
}
