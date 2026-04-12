package org.order.application.dto;

import org.order.domain.entity.Order;
import java.util.UUID;

public record OrderDetailInfo(
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
    public static OrderDetailInfo from(Order order) {
        return new OrderDetailInfo(
                order.getOrderId(),
                order.getShopperId(),
                order.getOrderStatus().name(),
                order.getTotalPrice(),
                order.getProduct().getProductId(),
                order.getProduct().getQuantity(),
                order.getDelivery().getAddress(),
                order.getDelivery().getDetailAddress(),
                order.getDelivery().getShopperName(),
                order.getDelivery().getMessage()
        );
    }
}
