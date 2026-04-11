package org.order.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.domain.entity.OrderEventLog;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventScheduler {

    private final OrderEventProcessor orderEventProcessor;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int MAX_RETRY = 3;
    private static final String ORDER_CREATED_TOPIC = "order-created";

    @Scheduled(fixedDelay = 5000)
    public void publishOrderEvents() {
        List<OrderEventLog> events = orderEventProcessor.getTargetEventsAndMarkPublishing();

        if (events.isEmpty()) {
            return;
        }

        events.forEach(this::sendToKafka);
    }

    private void sendToKafka(OrderEventLog event) {
        kafkaTemplate.send(ORDER_CREATED_TOPIC, event.getPayload())
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        orderEventProcessor.processSuccess(event.getEventId());
                        log.info("이벤트 발행 성공 - ID: {}", event.getEventId());
                        return;
                    }
                    orderEventProcessor.processFailure(event.getEventId(), MAX_RETRY);
                    log.error("이벤트 발행 실패 - ID: {}, 사유: {}", event.getEventId(), ex.getMessage());
                });
    }
}
