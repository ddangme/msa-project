package org.common.exception;

public record ErrorResponse(
        String code,
        String message
) {

    public static ErrorResponse of(BaseErrorCode baseErrorCode) {
        return new ErrorResponse(baseErrorCode.getCode(), baseErrorCode.getMessage());
    }
}
