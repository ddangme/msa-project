package org.product.application.dto;

import java.util.UUID;

public record OrderEventPayload(
        UUID orderId,
        UUID productId,
        int quantity
) {}
