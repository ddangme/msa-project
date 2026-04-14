package org.order.domain.exception;

import org.common.exception.BaseErrorCode;

public class OrderStatusException extends OrderException {

    public OrderStatusException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public OrderStatusException(BaseErrorCode baseErrorCode, String customMessage) {
        super(baseErrorCode, customMessage);
    }
}
