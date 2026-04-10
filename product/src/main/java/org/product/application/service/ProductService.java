package org.product.application.service;

import lombok.RequiredArgsConstructor;
import org.product.application.dto.CreateProductCommand;
import org.product.domain.entity.Product;
import org.product.domain.repository.ProductRepository;
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

}
