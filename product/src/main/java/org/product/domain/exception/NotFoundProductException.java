package org.product.domain.exception;

import org.common.exception.BaseErrorCode;

public class NotFoundProductException extends ProductException {

    public NotFoundProductException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
