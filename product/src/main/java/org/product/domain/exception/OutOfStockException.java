package org.product.domain.exception;

import org.common.exception.BaseErrorCode;

public class OutOfStockException extends ProductException {

    public OutOfStockException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public OutOfStockException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode, cause);
    }

    public OutOfStockException(BaseErrorCode baseErrorCode, String customMessage) {
        super(baseErrorCode, customMessage);
    }

    public OutOfStockException(BaseErrorCode baseErrorCode, String customMessage, Throwable cause) {
        super(baseErrorCode, customMessage, cause);
    }
}
