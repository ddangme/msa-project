package org.order.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.application.dto.CreateOrderCommand;
import org.order.application.dto.OrderDetailInfo;
import org.order.application.dto.OrderInfo;
import org.order.domain.entity.Order;
import org.order.domain.entity.OrderEventLog;
import org.order.domain.event.OrderEventPayload;
import org.order.domain.event.OrderEventType;
import org.order.domain.event.ProductEventPayload;
import org.order.domain.event.ProductEventType;
import org.order.domain.exception.OrderEventException;
import org.order.domain.repository.OrderEventLogRepository;
import org.order.domain.repository.OrderRepository;
import org.order.domain.repository.ProductClient;
import org.order.global.exception.OrderErrorCode;
import org.order.infrastructure.dto.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
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
        OrderEventPayload payload = new OrderEventPayload(
                orderId,
                command.productId(),
                command.quantity(),
                OrderEventType.ORDER_CREATED
        );
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

    @Transactional
    public void processProductResult(ProductEventPayload payload) {
        Order order = getOrder(payload.orderId());

        if (payload.status() == ProductEventType.STOCK_DEDUCTED_SUCCESS) {
            order.complete();
            log.info("주문 재고 확인 완료 (주문 완료) - OrderID: {}", order.getOrderId());

        } else if (payload.status() == ProductEventType.STOCK_DEDUCTED_FAILED) {
            order.cancelOrder();
            log.warn("재고 부족으로 인한 주문 보상 트랜잭션 (주문 취소) 완료 - OrderID: {}", order.getOrderId());
        }
    }

    public Page<OrderInfo> findOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderInfo::from);
    }

    public OrderDetailInfo findOrderDetail(UUID orderId) {
        return OrderDetailInfo.from(getOrder(orderId));
    }

    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderEventException(OrderErrorCode.ORDER_NOT_FOUND));
    }
}
