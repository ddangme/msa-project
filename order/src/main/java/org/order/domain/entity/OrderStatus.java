package org.order.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_CREATED("주문 생성"),
    ORDER_CONFIRMED("주문 확정"),
    ORDER_CANCEL("주문 취소"),
    ORDER_COMPLETED("배송 완료"),

    ;

    private final String description;
}
