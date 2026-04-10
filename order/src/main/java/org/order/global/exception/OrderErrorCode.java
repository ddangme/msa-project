package org.order.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.common.exception.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {

    OUT_OF_STOCK("ORDER-001", "재고가 부족합니다."),
    CANT_ORDER("ORDER-002", "구매할 수 없는 상품입니다."),

    ;

    private final String code;

    private final String message;
}
