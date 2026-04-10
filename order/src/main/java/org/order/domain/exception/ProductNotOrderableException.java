package org.order.domain.exception;

import org.common.exception.BaseErrorCode;

public class ProductNotOrderableException extends OrderException {

    public ProductNotOrderableException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

}
