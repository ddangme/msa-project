package org.order.domain.repository;

import org.order.infrastructure.dto.ProductInfo;

import java.util.UUID;

public interface ProductClient {
    ProductInfo getProduct(UUID productId);

}
