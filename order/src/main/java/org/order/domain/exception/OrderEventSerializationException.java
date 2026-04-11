package org.order.domain.exception;

import org.common.exception.BaseErrorCode;

public class OrderEventSerializationException extends OrderException {

    public OrderEventSerializationException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode, cause);
    }
}
