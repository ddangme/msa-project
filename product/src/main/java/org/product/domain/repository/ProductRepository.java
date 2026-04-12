package org.product.domain.repository;

import org.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(UUID productId);
}
