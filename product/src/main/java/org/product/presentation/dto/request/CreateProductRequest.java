package org.product.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.product.application.dto.CreateProductCommand;
import org.product.domain.constant.ProductPolicy;

public record CreateProductRequest(
        @NotBlank(message = ProductPolicy.PRODUCT_NAME_NULL_ERROR_MESSAGE)
        String name,

        @Min(value = ProductPolicy.MIN_STOCK, message = ProductPolicy.STOCK_RANGE_ERROR_MESSAGE)
        @Max(value = ProductPolicy.MAX_STOCK, message = ProductPolicy.STOCK_RANGE_ERROR_MESSAGE)
        int stock,

        @Min(value = ProductPolicy.MIN_PRICE, message = ProductPolicy.PRICE_RANGE_ERROR_MESSAGE)
        @Max(value = ProductPolicy.MAX_PRICE, message = ProductPolicy.PRICE_RANGE_ERROR_MESSAGE)
        int price
) {

    public CreateProductCommand toCommand() {
        return new CreateProductCommand(name, stock, price);
    }
}
