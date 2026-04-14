package org.order.domain.policy;

import org.order.domain.entity.OrderStatus;
import org.order.domain.exception.OrderStatusException;
import org.order.global.exception.OrderErrorCode;

public class CustomerOrderStatusPolicy implements OrderStatusPolicy {

    // switch 결과를 변수에 할당(표현식)하여, Enum 누락 시 컴파일 에러가 발생하도록 강제함

    @Override
    public void validateCancel(OrderStatus current) {
        var validated = switch (current) {
            case ORDER_CREATED -> true;
            case ORDER_CONFIRMED -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "확정된 주문은 취소할 수 없습니다.");
            case ORDER_COMPLETED -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "배송이 완료된 주문은 취소할 수 없습니다.");
            case ORDER_CANCEL -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "이미 취소된 주문입니다.");
        };
    }

    @Override
    public void validateConfirm(OrderStatus current) {
        var validated = switch (current) {
            case ORDER_CREATED -> true;
            case ORDER_CONFIRMED -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "이미 확정된 주문입니다.");
            case ORDER_COMPLETED -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "배송 완료된 주문은 확정할 수 없습니다.");
            case ORDER_CANCEL -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "취소된 주문은 확정할 수 없습니다.");
        };
    }

    @Override
    public void validateComplete(OrderStatus current) {
        var validated = switch (current) {
            case ORDER_CREATED, ORDER_CONFIRMED -> true;
            case ORDER_COMPLETED -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "이미 배송이 완료된 주문입니다.");
            case ORDER_CANCEL -> throw new OrderStatusException(OrderErrorCode.INVALID_STATUS_TRANSITION, "취소된 주문은 완료로 변경할 수 없습니다.");
        };
    }
}
