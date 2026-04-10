package org.order.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.order.domain.vo.Delivery;

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
    private UUID productId;

    @Embedded
    private Delivery delivery;

    @Builder(access = AccessLevel.PRIVATE)
    private Order(UUID sellerId, UUID shopperId, UUID productId, Delivery delivery) {
        this.sellerId = sellerId;
        this.shopperId = shopperId;
        this.orderStatus = OrderStatus.ORDER_ACCEPT;
        this.productId = productId;
        this.delivery = delivery;
    }

    public static Order create(UUID sellerId, UUID shopperId, UUID productId, Delivery delivery) {
        return Order.builder()
                .sellerId(sellerId)
                .shopperId(shopperId)
                .productId(productId)
                .delivery(delivery)
                .build();
    }

}
