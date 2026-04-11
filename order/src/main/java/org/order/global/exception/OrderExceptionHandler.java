package org.order.global.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.common.response.CommonResponse;
import org.order.domain.exception.OrderException;
import org.order.domain.exception.ProductNotOrderableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(ProductNotOrderableException.class)
    public ResponseEntity<CommonResponse<Void>> handleCantOrderException(ProductNotOrderableException e) {
        log.warn("주문 불가 예외 발생: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<CommonResponse<Void>> handleOrderException(OrderException e) {
        log.warn("주문 불가 예외 발생: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleGeneralException(Exception e) {
        log.error("서버 내부 오류 발생", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<CommonResponse<Void>> handleFeignException(FeignException e) {

        return ResponseEntity
                .status(e.status())
                .body(CommonResponse.fail("상품 서버 처리 중 오류가 발생했습니다."));
    }
}
