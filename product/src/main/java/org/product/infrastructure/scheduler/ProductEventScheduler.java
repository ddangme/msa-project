package org.product.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.product.domain.entity.ProductEventLog;
import org.product.domain.entity.ProductEventStatus;
import org.product.domain.repository.ProductEventLogRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventScheduler {

    private final ProductEventLogRepository productEventLogRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishProductEvents() {
        List<ProductEventLog> allEvent = productEventLogRepository.findByStatus(ProductEventStatus.INIT);

        if (allEvent.isEmpty()) {
            return;
        }

        log.info("발행 대기 중인 상품 이벤트: {}건", allEvent.size());

        for (ProductEventLog event : allEvent) {
            try {
                kafkaTemplate.send("product-stock-result", event.getPayload());
                event.markAsPublish();
                log.info("상품 이벤트 발행 성공 - ID: {}, Type: {}", event.getEventId(), event.getEventType());
            } catch (Exception e) {
                log.error("상품 이벤트 발행 실패 - ID: {}, 사유: {}", event.getEventId(), e.getMessage());
            }
        }
    }
}
