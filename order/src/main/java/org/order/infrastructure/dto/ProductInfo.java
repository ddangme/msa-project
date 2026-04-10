package org.order.infrastructure.dto;

import java.util.UUID;

public record ProductInfo(
        UUID productId,
        String name,
        int stock,
        int price,
        boolean isOrderable
) {
}
