package org.product.application.dto;

import org.product.domain.entity.Product;

import java.util.UUID;

public record ProductInfo(
        UUID productId,
        String name,
        int stock,
        int price
) {

    public static ProductInfo from(Product product) {
        return new ProductInfo(
                product.getProductId(),
                product.getName(),
                product.getStock(),
                product.getPrice()
        );
    }
}
