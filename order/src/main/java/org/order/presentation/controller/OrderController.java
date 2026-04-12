package org.order.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.common.response.CommonResponse;
import org.order.application.dto.OrderDetailInfo;
import org.order.application.dto.OrderInfo;
import org.order.application.service.OrderService;
import org.order.presentation.dto.request.CreateOrderRequest;
import org.order.presentation.dto.response.OrderDetailResponse;
import org.order.presentation.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<CommonResponse<Page<OrderResponse>>> findOrders(Pageable pageable) {
        org.springframework.data.domain.Page<OrderInfo> infos = orderService.findOrders(pageable);

        return ResponseEntity.ok(CommonResponse.success(infos.map(OrderResponse::from)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetailResponse>> findOrderDetail(@PathVariable UUID orderId) {
        OrderDetailInfo info = orderService.findOrderDetail(orderId);

        return ResponseEntity.ok(CommonResponse.success(OrderDetailResponse.from(info)));
    }

}
