package org.order.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.domain.entity.OrderEventStatus;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.repository.OrderEventLogRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderEventScheduler {

    private final OrderEventLogRepository orderEventLogRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int MAX_RETRY = 3;

    @Scheduled(fixedDelay = 5000) // 5초
    public void publishOrderEvents() {
        List<OrderEventLog> allEvent = orderEventLogRepository.findByStatus(OrderEventStatus.INIT);

        if (allEvent.isEmpty()) {
            return;
        }

        for (OrderEventLog event : allEvent) {
            kafkaTemplate.send("order-created", event.getPayload())
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            handleSuccess(event.getEventId());
                        } else {
                            handleFailure(event.getEventId(), ex.getMessage());
                        }
                    });
        }
    }

    @Transactional
    public void handleSuccess(UUID eventId) {
        OrderEventLog event = orderEventLogRepository.findById(eventId);
        event.markAsPublish();
        log.info("이벤트 발행 성공 - ID: {}", eventId);
    }

    @Transactional
    public void handleFailure(UUID eventId, String errorMsg) {
        OrderEventLog event = orderEventLogRepository.findById(eventId);
        event.increaseRetryCount(MAX_RETRY);
        log.error("이벤트 발행 실패 - ID: {}, 사유: {}, 시도 횟수: {}", eventId, errorMsg, event.getRetryCount());
    }
}