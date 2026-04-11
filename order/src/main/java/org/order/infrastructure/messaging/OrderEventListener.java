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

    @KafkaListener(topics = "product-stock-result", groupId = "order-service-group")
    public void consumeProductResult(String message) {
        try {
            ProductEventPayload payload = objectMapper.readValue(message, ProductEventPayload.class);
            log.info("상품 서버 결과 수신 - OrderID: {}, Status: {}", payload.orderId(), payload.status());
            orderService.processProductResult(payload);
        } catch (JsonProcessingException e) {
            log.error("상품 응답 메시지 파싱 실패: {}", e.getMessage(), e);
        }
    }
}
