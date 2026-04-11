package org.order.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.common.exception.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {

    OUT_OF_STOCK("ORDER-001", "재고가 부족합니다."),
    CANT_ORDER("ORDER-002", "구매할 수 없는 상품입니다."),
    EVENT_SERIALIZATION_FAIL("ORDER-003", "이벤트 페이로드 직렬화에 실패했습니다."),
    ORDER_EVENT_NOT_FOUND("ORDER-004", "존재하지 않는 이벤트입니다."),

    ;

    private final String code;

    private final String message;
}
