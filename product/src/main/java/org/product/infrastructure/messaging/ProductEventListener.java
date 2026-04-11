package org.product.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.product.application.dto.OrderEventPayload;
import org.product.application.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    @KafkaListener(topics = "order-created", groupId = "product-service-group")
    public void consumeOrderCreated(String message) {
        try {
            log.info("카프카 메시지 수신 성공: {}", message);

            OrderEventPayload payload = objectMapper.readValue(message, OrderEventPayload.class);
            productService.processOrderEvent(payload);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize order event message: {}", e.getMessage(), e);
        }
    }
}
