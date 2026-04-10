package org.product.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.product.application.dto.CreateProductCommand;
import org.product.domain.constant.ProductPolicy;

public record CreateProductRequest(
        @NotBlank(message = "상품명은 필수입니다.")
        String name,

        @Min(value = ProductPolicy.MIN_STOCK, message = "재고는 " + ProductPolicy.MIN_STOCK + " 이상이어야 합니다.")
        @Max(value = ProductPolicy.MAX_STOCK, message = "재고는 " + ProductPolicy.MAX_STOCK + " 이하여야 합니다.")
        int stock,

        @Min(value = ProductPolicy.MIN_PRICE, message = "가격은 " + ProductPolicy.MIN_PRICE +"원 이상이어야 합니다.")
        @Max(value = ProductPolicy.MAX_PRICE, message = "가격은 " + ProductPolicy.MAX_PRICE +"원 이하여야 합니다.")
        int price
) {

    public CreateProductCommand toCommand() {
        return new CreateProductCommand(name, stock, price);
    }
}
