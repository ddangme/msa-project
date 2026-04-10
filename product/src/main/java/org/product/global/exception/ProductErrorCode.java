package org.product.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.common.exception.BaseErrorCode;
import org.product.domain.constant.ProductPolicy;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    PRODUCT_NAME_IS_EMPTY("PRODUCT-001", ProductPolicy.PRODUCT_NAME_NULL_ERROR_MESSAGE),
    INVALID_PRODUCT_STOCK("PRODUCT-002", ProductPolicy.STOCK_RANGE_ERROR_MESSAGE),
    INVALID_PRODUCT_PRICE("PRODUCT-003", ProductPolicy.PRICE_RANGE_ERROR_MESSAGE),
    OUT_OF_STOCK("PRODUCT-004", "재고가 부족합니다."),


    ;

    private final String code;

    private final String message;

}
