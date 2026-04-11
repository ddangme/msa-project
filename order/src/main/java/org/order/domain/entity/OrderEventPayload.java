package org.order.domain.entity;

import java.util.UUID;

public record OrderEventPayload(
        UUID orderId,
        UUID productId,
        int quantity
) {}
