package org.order.presentation.dto.request;

import org.order.application.dto.CreateOrderCommand;

import java.util.UUID;

public record CreateOrderRequest(
        UUID sellerId,
        UUID shopperId,
        UUID productId,
        int quantity,
        int totalPrice,
        String address,
        String detailAddress,
        String shopperName,
        String message
) {

    public CreateOrderCommand toCommand() {
        return new CreateOrderCommand(
                sellerId,
                shopperId,
                productId,
                quantity,
                totalPrice,
                address,
                detailAddress,
                shopperName,
                message
        );
    }
}
