package org.order.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        this.orderStatus = OrderStatus.ORDER_ACCEPT;
        this.totalPrice = totalPrice;
        this.product = product;
        this.delivery = delivery;
    }

    public static Order create(UUID sellerId, UUID shopperId, Product product, Delivery delivery, Long totalPrice) {
        return Order.builder()
                .sellerId(sellerId)
                .shopperId(shopperId)
                .product(product)
                .delivery(delivery)
                .totalPrice(totalPrice)
                .build();
    }

}
