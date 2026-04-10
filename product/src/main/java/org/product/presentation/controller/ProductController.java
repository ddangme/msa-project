package org.product.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.common.response.CommonResponse;
import org.product.application.dto.ProductInfo;
import org.product.application.service.ProductService;
import org.product.presentation.dto.request.CreateProductRequest;
import org.product.presentation.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<UUID>> createProduct(@RequestBody @Valid CreateProductRequest request) {
        UUID productId = productService.createProduct(request.toCommand());

        return ResponseEntity.ok(CommonResponse.success(productId));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Page<ProductResponse>>> findProduct(Pageable pageable) {
        Page<ProductInfo> responses = productService.find(pageable);

        return ResponseEntity.ok(CommonResponse.success(responses.map(ProductResponse::from)));
    }

}
