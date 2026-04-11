package org.product.domain.event;

import java.util.UUID;

public record OrderEventPayload(
        UUID orderId,
        UUID productId,
        int quantity,
        OrderEventType eventType
) {}
