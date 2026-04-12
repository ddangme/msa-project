package org.product.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.product.domain.event.OrderEventPayload;
import org.product.application.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    @KafkaListener(topics = "${app.kafka.topics.order-created}")
    public void consumeOrderCreated(String message) {
        try {
            log.info("주문 생성 카프카 메시지 수신 성공: {}", message);
            OrderEventPayload payload = objectMapper.readValue(message, OrderEventPayload.class);
            productService.processOrderEvent(payload);
        } catch (JsonProcessingException e) {
            log.error("주문 생성 이벤트 메시지 역직렬화 실패: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "${app.kafka.topics.order-cancelled}")
    public void consumeOrderCancelled(String message) {
        try {
            log.info("주문 취소 카프카 메시지 수신 성공: {}", message);
            OrderEventPayload payload = objectMapper.readValue(message, OrderEventPayload.class);
            productService.processOrderCancelled(payload);
        } catch (JsonProcessingException e) {
            log.error("주문 취소 이벤트 메시지 역직렬화 실패: {}", e.getMessage(), e);
        }
    }
}
