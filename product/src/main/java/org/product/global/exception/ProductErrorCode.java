package org.product.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.common.exception.BaseErrorCode;
import org.product.domain.constant.ProductPolicy;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    PRODUCT_NAME_IS_EMPTY("PRODUCT-001","상품명은 필수입니다."),
    INVALID_PRODUCT_STOCK("PRODUCT-002", "유효하지 않은 재고 수량입니다."),
    INVALID_PRODUCT_PRICE("PRODUCT-003", "유효하지 않은 가격입니다."),
    OUT_OF_STOCK("PRODUCT-004", "재고가 부족합니다."),

    ;

    private final String code;

    private final String message;

}
