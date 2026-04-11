package org.order.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.order.application.dto.CreateOrderCommand;
import org.order.domain.entity.Order;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.entity.OrderEventPayload;
import org.order.domain.exception.OrderEventException;
import org.order.domain.repository.OrderEventLogRepository;
import org.order.domain.repository.OrderRepository;
import org.order.domain.repository.ProductClient;
import org.order.global.exception.OrderErrorCode;
import org.order.infrastructure.dto.ProductInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventLogRepository orderEventLogRepository;
    private final ProductClient productClient;
    private final ObjectMapper objectMapper;

    @Transactional
    public UUID createOrder(CreateOrderCommand command) {
        ProductInfo product = productClient.getProduct(command.productId());
        product.validateOrderable(command.quantity());

        Order savedOrder = orderRepository.save(command.toEntity(product.price()));
        saveOrderEvent(savedOrder.getOrderId(), command);

        return savedOrder.getOrderId();
    }

    private void saveOrderEvent(UUID orderId, CreateOrderCommand command) {
        OrderEventPayload payload = new OrderEventPayload(orderId, command.productId(), command.quantity());
        String serializedPayload = serializePayload(payload);

        orderEventLogRepository.save(OrderEventLog.create(orderId, serializedPayload));
    }

    private String serializePayload(OrderEventPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new OrderEventException(OrderErrorCode.EVENT_SERIALIZATION_FAIL, e);
        }
    }
}
