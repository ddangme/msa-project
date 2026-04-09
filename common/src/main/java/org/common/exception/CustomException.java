package org.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final BaseErrorCode baseErrorCode;

    public CustomException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getMessage());
        this.baseErrorCode = baseErrorCode;
    }

    public CustomException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode.getMessage(), cause);
        this.baseErrorCode = baseErrorCode;
    }

    public CustomException(BaseErrorCode baseErrorCode, String customMessage) {
        super(customMessage);
        this.baseErrorCode = baseErrorCode;
    }

    public CustomException(BaseErrorCode baseErrorCode, String customMessage, Throwable cause) {
        super(customMessage, cause);
        this.baseErrorCode = baseErrorCode;
    }

}