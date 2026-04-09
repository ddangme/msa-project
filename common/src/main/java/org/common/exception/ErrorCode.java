package org.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    // 공통 에러 (COMMON)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 요청 형식입니다."),
    MISSING_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-002", "필수 입력값이 누락되었습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON-003", "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-004", "서버 내부 오류가 발생했습니다."),

    // 리소스 관련 (NOT_FOUND)
    NOT_FOUND(HttpStatus.NOT_FOUND, "RES-001", "요청한 리소스를 찾을 수 없습니다."),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
