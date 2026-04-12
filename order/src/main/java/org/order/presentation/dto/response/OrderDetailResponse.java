package org.order.presentation.dto.response;

import org.order.application.dto.OrderDetailInfo;

import java.util.UUID;

public record OrderDetailResponse(
        UUID orderId,
        UUID shopperId,
        String orderStatus,
        Long totalPrice,
        UUID productId,
        int quantity,
        String address,
        String detailAddress,
        String shopperName,
        String message
) {
    public static OrderDetailResponse from(OrderDetailInfo info) {
        return new OrderDetailResponse(
                info.orderId(),
                info.shopperId(),
                info.orderStatus(),
                info.totalPrice(),
                info.product().getProductId(),
                info.product().getQuantity(),
                info.delivery().getAddress(),
                info.delivery().getDetailAddress(),
                info.delivery().getShopperName(),
                info.delivery().getMessage()
        );
    }
}

