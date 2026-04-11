package org.product.application.dto;

import java.util.UUID;

public record ProductEventPayload(
        UUID orderId,
        UUID productId,
        String status
) {}
