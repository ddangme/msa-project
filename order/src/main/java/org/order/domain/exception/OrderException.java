package org.order.domain.exception;

import org.common.exception.BaseErrorCode;
import org.common.exception.CustomException;

public abstract class OrderException extends CustomException {

    public OrderException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }

    public OrderException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode, cause);
    }

    public OrderException(BaseErrorCode baseErrorCode, String customMessage) {
        super(baseErrorCode, customMessage);
    }

    public OrderException(BaseErrorCode baseErrorCode, String customMessage, Throwable cause) {
        super(baseErrorCode, customMessage, cause);
    }
}
