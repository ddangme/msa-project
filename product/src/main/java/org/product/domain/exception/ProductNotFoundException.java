package org.product.domain.exception;

import org.common.exception.BaseErrorCode;

public class ProductNotFoundException extends ProductException {

    public ProductNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
