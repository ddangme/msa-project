package org.order.domain.event;

import lombok.Getter;

@Getter
public enum OrderEventType {
    ORDER_CREATED,
    ORDER_CANCELLED,
    ORDER_FAILED
}
