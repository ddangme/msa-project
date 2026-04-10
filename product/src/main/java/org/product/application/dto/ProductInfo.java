package org.product.application.dto;

import org.product.domain.entity.Product;
import org.product.domain.entity.ProductStatus;

import java.util.UUID;

public record ProductInfo(
        UUID productId,
        String name,
        int stock,
        int price,
        ProductStatus status
) {

    public static ProductInfo from(Product product) {
        return new ProductInfo(
                product.getProductId(),
                product.getName(),
                product.getStock(),
                product.getPrice(),
                product.getProductStatus()
        );
    }
}
