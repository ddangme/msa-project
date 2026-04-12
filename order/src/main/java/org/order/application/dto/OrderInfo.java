package org.order.application.dto;

import org.order.domain.entity.Order;

import java.util.UUID;

public record OrderInfo(
        UUID orderId,
        UUID shopperId,
        String orderStatus
) {

    public static OrderInfo from(Order order) {
        return new OrderInfo(
                order.getOrderId(),
                order.getShopperId(),
                order.getOrderStatus().toString()
        );
    }
}
