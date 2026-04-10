package org.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.product.domain.constant.ProductPolicy;
import org.product.domain.exception.InvalidProductException;
import org.product.domain.exception.OutOfStockException;
import org.product.global.exception.ProductErrorCode;

import java.util.UUID;

@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    private static final Integer MIN_STOCK = ProductPolicy.MIN_STOCK;
    private static final Integer MAX_STOCK = ProductPolicy.MAX_STOCK;
    private static final Integer MIN_PRICE = ProductPolicy.MIN_PRICE;
    private static final Integer MAX_PRICE = ProductPolicy.MAX_PRICE;

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Builder(access = AccessLevel.PRIVATE)
    private Product(String name, int stock, int price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.productStatus = ProductStatus.PREPARING;
    }

    public static Product create(String name, int stock, int price) {
        validateProduct(name, stock, price);

        return Product.builder()
                .name(name)
                .stock(stock)
                .price(price)
                .build();
    }

    public boolean isOrderable() {
        return this.productStatus == ProductStatus.ON_SALE && this.stock > 0;
    }

    public void decreaseStock(int quantity) {
        if (this.stock - quantity < MIN_STOCK) {
            throw new OutOfStockException(ProductErrorCode.OUT_OF_STOCK);
        }

        this.stock -= quantity;

        if (this.stock == 0) {
            this.productStatus = ProductStatus.SOLD_OUT;
        }
    }

    public void restoreStock(int quantity) {
        this.stock += quantity;

        if (this.productStatus == ProductStatus.SOLD_OUT && this.stock > MIN_STOCK) {
            this.productStatus = ProductStatus.ON_SALE;
        }
    }

    private static void validateProduct(String name, int stock, long price) {
        if (name == null || name.isBlank()) {
            throw new InvalidProductException(ProductErrorCode.PRODUCT_NAME_IS_EMPTY);
        }

        if (stock < MIN_STOCK || stock > MAX_STOCK) {
            throw new InvalidProductException(ProductErrorCode.INVALID_PRODUCT_STOCK);
        }

        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new InvalidProductException(ProductErrorCode.INVALID_PRODUCT_PRICE);
        }
    }
}
