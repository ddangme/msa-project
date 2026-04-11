package org.order.infrastructure.dto;

import org.order.domain.exception.ProductNotOrderableException;
import org.order.global.exception.OrderErrorCode;

import java.util.UUID;

public record ProductInfo(
        UUID productId,
        String name,
        int stock,
        int price,
        boolean isOrderable
) {
    public void validateOrderable(int quantity) {
        if (!this.isOrderable || this.stock < quantity) {
            throw new ProductNotOrderableException(OrderErrorCode.CANT_ORDER);
        }
    }
}
