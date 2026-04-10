package org.product.application.dto;

public record CreateProductCommand(
        String name,
        int stock,
        int price
) {
}
