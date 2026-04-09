package org.product.domain.exception;

import org.common.exception.BaseErrorCode;

public class InvalidProductException extends ProductException {

    public InvalidProductException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

}
