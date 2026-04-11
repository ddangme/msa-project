package org.order.domain.exception;

import org.common.exception.BaseErrorCode;

public class OrderNotFoundException extends OrderException {

    public OrderNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

}
