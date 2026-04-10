package org.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private int quantity;

    public Product(int quantity, UUID productId) {
        this.quantity = quantity;
        this.productId = productId;
    }
}
