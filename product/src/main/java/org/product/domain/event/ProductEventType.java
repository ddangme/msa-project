package org.product.domain.event;

import lombok.Getter;

@Getter
public enum ProductEventType {
    STOCK_DEDUCTED_SUCCESS,
    STOCK_DEDUCTED_FAILED
}
