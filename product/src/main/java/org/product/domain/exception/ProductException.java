package org.product.domain.exception;

import org.common.exception.BaseErrorCode;
import org.common.exception.CustomException;

public abstract class ProductException extends CustomException {

    public ProductException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public ProductException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode, cause);
    }

    public ProductException(BaseErrorCode baseErrorCode, String customMessage) {
        super(baseErrorCode, customMessage);
    }

    public ProductException(BaseErrorCode baseErrorCode, String customMessage, Throwable cause) {
        super(baseErrorCode, customMessage, cause);
    }
}
