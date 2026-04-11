package org.order.global.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.common.response.CommonResponse;
import org.order.domain.exception.OrderEventException;
import org.order.domain.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<CommonResponse<Void>> handleOrderException(OrderException e) {
        log.warn("Business Exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(OrderEventException.class)
    public ResponseEntity<CommonResponse<Void>> handleOrderEventException(OrderEventException e) {
        log.error("Event Processing Exception: ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<CommonResponse<Void>> handleFeignException(FeignException e) {
        log.error("FeignException 발생: status={}, message={}", e.status(), e.getMessage());

        return ResponseEntity
                .status(resolveFeignHttpStatus(e.status()))
                .body(CommonResponse.fail("외부 서비스 연동 중 오류가 발생했습니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Validation Exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleInternalServerException(Exception e) {
        log.error("Uncaught Exception: ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail("서버 내부 오류가 발생했습니다."));
    }

    private HttpStatus resolveFeignHttpStatus(int status) {
        if (status < 0) {
            return HttpStatus.BAD_GATEWAY;
        }
        HttpStatus resolvedStatus = HttpStatus.resolve(status);
        return resolvedStatus != null ? resolvedStatus : HttpStatus.BAD_GATEWAY;
    }

}
