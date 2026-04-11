package org.order.domain.entity;

import lombok.Getter;

@Getter
public enum EventType {
    ORDER_CREATED,
    ORDER_CANCELLED,
    ORDER_FAILED
}
