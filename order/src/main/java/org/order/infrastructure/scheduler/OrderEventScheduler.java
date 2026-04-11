package org.order.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.domain.entity.EventStatus;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.repository.OrderEventLogRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventScheduler {

    private final OrderEventLogRepository orderEventLogRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000) // 5초
    @Transactional
    public void publishOrderEvents() {
        List<OrderEventLog> allEvent = orderEventLogRepository.findByStatus(EventStatus.INIT);

        if (allEvent.isEmpty()) {
            return;
        }

        log.info("발행 대기 중인 이벤트: {}건", allEvent.size());

        for (OrderEventLog event : allEvent) {
            try {
                kafkaTemplate.send("order-created", event.getPayload());

                event.completePublish();
                log.info("이벤트 발행 성공 - ID: {}, Type: {}", event.getEventId(), event.getEventType());
            } catch (Exception e) {
                log.error("이벤트 발행 실패 - ID: {}, 사유: {}", event.getEventId(), e.getMessage());
            }
        }
    }
}
