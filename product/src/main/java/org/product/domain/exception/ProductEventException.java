package org.product.domain.exception;

import org.common.exception.BaseErrorCode;

public class ProductEventException extends ProductException {

    public ProductEventException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public ProductEventException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode, cause);
    }
}
