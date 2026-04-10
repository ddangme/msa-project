package org.order.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.order.domain.repository.ProductClient;
import org.order.infrastructure.client.ProductFeignClient;
import org.order.infrastructure.dto.ProductInfo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductClient {

    private final ProductFeignClient productFeignClient;

    @Override
    public ProductInfo getProduct(UUID productId) {
        return productFeignClient.getProduct(productId).getData();
    }
}
