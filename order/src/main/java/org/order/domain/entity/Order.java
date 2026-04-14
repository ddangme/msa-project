package org.order.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.order.domain.policy.CustomerOrderStatusPolicy;
import org.order.domain.policy.OrderStatusPolicy;
import org.order.domain.vo.Delivery;
import org.order.domain.vo.Product;

import java.util.UUID;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(nullable = false)
    private UUID sellerId;

    @Column(nullable = false)
    private UUID shopperId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private Long totalPrice;

    @Embedded
    private Product product;

    @Embedded
    private Delivery delivery;

    @Builder(access = AccessLevel.PRIVATE)
    private Order(UUID sellerId, UUID shopperId, Product product, Delivery delivery, Long totalPrice) {
        this.sellerId = sellerId;
        this.shopperId = shopperId;
        this.orderStatus = OrderStatus.ORDER_CREATED;
        this.totalPrice = totalPrice;
        this.product = product;
        this.delivery = delivery;
    }

    public static Order create(UUID sellerId, UUID shopperId, Product product, Delivery delivery, long totalPrice) {
        return Order.builder()
                .sellerId(sellerId)
                .shopperId(shopperId)
                .product(product)
                .delivery(delivery)
                .totalPrice(totalPrice)
                .build();
    }

    public void cancel(OrderStatusPolicy policy) {
        policy.validateCancel(this.orderStatus);
        this.orderStatus = OrderStatus.ORDER_CANCEL;
    }

    public void confirm(OrderStatusPolicy policy) {
        policy.validateConfirm(this.orderStatus);
        this.orderStatus = OrderStatus.ORDER_CONFIRMED;
    }

    public void completed(OrderStatusPolicy policy) {
        policy.validateComplete(this.orderStatus);
        this.orderStatus = OrderStatus.ORDER_COMPLETED;
    }

}
