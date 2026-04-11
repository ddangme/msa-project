package org.product.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.product.domain.entity.ProductEventLog;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventScheduler {

    private final ProductEventProcessor productEventProcessor;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int MAX_RETRY = 3;
    private static final String PRODUCT_STOCK_TOPIC = "product-stock-result";

    @Scheduled(fixedDelay = 5000)
    public void publishProductEvents() {
        List<ProductEventLog> events = productEventProcessor.getTargetEventsAndMarkPublishing();

        if (events.isEmpty()) {
            return;
        }

        events.forEach(this::sendToKafka);
    }

    private void sendToKafka(ProductEventLog event) {
        kafkaTemplate.send(PRODUCT_STOCK_TOPIC, event.getPayload())
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        productEventProcessor.processSuccess(event.getEventId());
                        log.info("상품 이벤트 발행 성공 - ID: {}", event.getEventId());
                        return;
                    }
                    productEventProcessor.processFailure(event.getEventId(), MAX_RETRY);
                    log.error("상품 이벤트 발행 실패 - ID: {}, 사유: {}", event.getEventId(), ex.getMessage());
                });
    }
}
