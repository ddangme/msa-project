package org.order.domain.event;

import java.util.UUID;

public record ProductEventPayload(
        UUID orderId,
        UUID productId,
        ProductEventType status
) {}