package org.order.presentation.dto.response;

import org.order.application.dto.OrderInfo;

import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID shopperId,
        String orderStatus
) {

    public static OrderResponse from(OrderInfo info) {
        return new OrderResponse(
                info.orderId(),
                info.shopperId(),
                info.orderStatus()
        );
    }
}
