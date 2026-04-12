package org.order.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.application.service.OrderService;
import org.order.domain.event.ProductEventPayload;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @KafkaListener(topics = "${app.kafka.topics.product-stock-result}")
    public void consumeProductResult(String message) {
        try {
            log.info("상품 재고 처리 결과 메시지 수신 성공: {}", message);
            ProductEventPayload payload = objectMapper.readValue(message, ProductEventPayload.class);
            orderService.processProductResult(payload);
        } catch (JsonProcessingException e) {
            log.error("상품 재고 처리 결과 메시지 역직렬화 실패: {}", e.getMessage(), e);
        }
    }
}
