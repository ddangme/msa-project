package org.order.application.dto;

import org.order.domain.entity.Order;
import org.order.domain.vo.Delivery;
import org.order.domain.vo.Product;

import java.util.UUID;

public record CreateOrderCommand(
        UUID sellerId,
        UUID shopperId,
        UUID productId,
        int quantity,
        String address,
        String detailAddress,
        String shopperName,
        String message
) {

    public Order toEntity(Long totalPrice) {
        return Order.create(sellerId, shopperId, toProduct(), toDelivery(), totalPrice);
    }

    private Product toProduct() {
        return new Product(quantity, productId);
    }

    private Delivery toDelivery() {
        return new Delivery(address, detailAddress, shopperName, message);
    }
}
