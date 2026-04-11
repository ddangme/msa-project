package org.order.domain.exception;

import org.common.exception.BaseErrorCode;

public class OrderEventException extends OrderException {

    public OrderEventException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public OrderEventException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode, cause);
    }
}
