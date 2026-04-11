package org.product.domain.event;

import java.util.UUID;

public record ProductEventPayload(
        UUID orderId,
        UUID productId,
        String status
) {}
