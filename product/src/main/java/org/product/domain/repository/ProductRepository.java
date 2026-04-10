package org.product.domain.repository;

import org.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);
}
