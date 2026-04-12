package org.order.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.event.OrderEventType;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.kafka.topics.order-created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.topics.order-cancelled}")
    private String orderCancelledTopic;

    private static final int MAX_RETRY = 3;

    @Scheduled(fixedDelay = 5000)
    public void publishOrderEvents() {
        List<OrderEventLog> events = orderEventProcessor.getTargetEventsAndMarkPublishing();

        if (events.isEmpty()) {
            return;
        }

        events.forEach(this::sendToKafka);
    }

    private void sendToKafka(OrderEventLog event) {
        String topic = resolveTopic(event.getOrderEventType());

        kafkaTemplate.send(topic, event.getPayload())
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        orderEventProcessor.processSuccess(event.getEventId());
                        log.info("이벤트 발행 성공 - ID: {}, Topic: {}", event.getEventId(), topic);
                        return;
                    }
                    orderEventProcessor.processFailure(event.getEventId(), MAX_RETRY);
                    log.error("이벤트 발행 실패 - ID: {}, Topic: {}, 사유: {}", event.getEventId(), topic, ex.getMessage());
                });
    }

    private String resolveTopic(OrderEventType eventType) {
        if (eventType == OrderEventType.ORDER_CANCELLED) {
            return orderCancelledTopic;
        }
        return orderCreatedTopic;
    }
}
