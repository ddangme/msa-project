package org.order.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.order.application.dto.CreateOrderCommand;
import org.order.domain.entity.Order;
import org.order.domain.exception.ProductNotOrderableException;
import org.order.domain.repository.OrderRepository;
import org.order.domain.repository.ProductClient;
import org.order.global.exception.OrderErrorCode;
import org.order.infrastructure.dto.ProductInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public UUID createOrder(CreateOrderCommand command) {
        ProductInfo product = productClient.getProduct(command.productId());
        log.info("product={}", product);

        if (!product.isOrderable()) {
            throw new ProductNotOrderableException(OrderErrorCode.CANT_ORDER);
        }

        if (command.quantity() > product.stock()) {
            throw new ProductNotOrderableException(OrderErrorCode.CANT_ORDER);
        }

        Order order = command.toEntity(product.price());

        // TODO: 주문 완료 시 product 도메인으로 재고 감소 이벤트 발송

        return orderRepository.save(order).getOrderId();
    }


}
