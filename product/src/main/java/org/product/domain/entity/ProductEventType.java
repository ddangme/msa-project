package org.product.domain.entity;

import lombok.Getter;

@Getter
public enum ProductEventType {
    STOCK_DEDUCTED_SUCCESS,
    STOCK_DEDUCTED_FAILED
}
