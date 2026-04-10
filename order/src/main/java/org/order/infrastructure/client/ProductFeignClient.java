package org.order.infrastructure.client;

import org.common.response.CommonResponse;
import org.order.infrastructure.dto.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product-server")
public interface ProductFeignClient {

    @GetMapping("/api/v1/products/{productId}")
    CommonResponse<ProductInfo> getProduct(@PathVariable UUID productId);
}
