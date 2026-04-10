package org.order.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.common.response.CommonResponse;
import org.order.application.service.OrderService;
import org.order.presentation.dto.request.CreateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponse<UUID>> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        UUID orderId = orderService.createOrder(request.toCommand());
        return ResponseEntity.ok(CommonResponse.success(orderId));
    }
}
