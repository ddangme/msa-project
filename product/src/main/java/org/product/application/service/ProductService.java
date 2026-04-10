package org.product.application.service;

import lombok.RequiredArgsConstructor;
import org.product.application.dto.CreateProductCommand;
import org.product.application.dto.ProductInfo;
import org.product.domain.entity.Product;
import org.product.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public UUID createProduct(CreateProductCommand command) {
        Product product = Product.create(command.name(), command.stock(), command.price());
        return productRepository.save(product).getProductId();
    }

    public Page<ProductInfo> find(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductInfo::from);
    }

    public ProductInfo find(UUID productId) {
        return ProductInfo.from(productRepository.findById(productId));
    }
}
