package org.product.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.common.response.CommonResponse;
import org.product.application.service.ProductService;
import org.product.presentation.dto.request.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
