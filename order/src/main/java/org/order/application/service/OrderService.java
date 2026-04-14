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
import org.order.domain.entity.OrderStatus;
import org.order.domain.event.OrderEventPayload;
import org.order.domain.event.OrderEventType;
import org.order.domain.event.ProductEventPayload;
import org.order.domain.event.ProductEventType;
import org.order.domain.exception.OrderEventException;
import org.order.domain.exception.OrderNotFoundException;
import org.order.domain.repository.OrderEventLogRepository;
import org.order.domain.repository.OrderRepository;
import org.order.global.exception.OrderErrorCode;
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
    private final ObjectMapper objectMapper;

    @Transactional
    public UUID createOrder(CreateOrderCommand command) {
        Order savedOrder = orderRepository.save(command.toEntity());
        saveOrderEvent(savedOrder.getOrderId(), command.productId(), command.quantity(), OrderEventType.ORDER_CREATED);

        log.info("주문 생성 완료 - OrderID: {}", savedOrder.getOrderId());
        return savedOrder.getOrderId();
    }

    @Transactional
    public void cancelOrder(UUID orderId) {
        Order order = getOrder(orderId);

        if (order.getOrderStatus() == OrderStatus.ORDER_CANCEL) {
            log.warn("이미 취소된 주문에 대한 취소 요청 - OrderID: {}", orderId);
            return;
        }

        order.cancelOrder();
        saveOrderEvent(order.getOrderId(), order.getProduct().getProductId(), order.getProduct().getQuantity(), OrderEventType.ORDER_CANCELLED);

        log.info("주문 취소 및 이벤트 저장 완료 - OrderID: {}", order.getOrderId());
    }

    private void saveOrderEvent(UUID orderId, UUID productId, int quantity, OrderEventType eventType) {
        OrderEventPayload payload = new OrderEventPayload(orderId, productId, quantity, eventType);
        String serializedPayload = serializePayload(payload);

        orderEventLogRepository.save(OrderEventLog.create(orderId, eventType, serializedPayload));
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
                .orElseThrow(() -> new OrderNotFoundException(OrderErrorCode.ORDER_NOT_FOUND));
    }
}
