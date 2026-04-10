package org.product.presentation.dto.response;

import org.product.application.dto.ProductInfo;

import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        int stock,
        int price,
        String status
) {

    public static ProductResponse from(ProductInfo info) {
        return new ProductResponse(
                info.productId(),
                info.name(),
                info.stock(),
                info.price(),
                info.status().name()
        );
    }

}
