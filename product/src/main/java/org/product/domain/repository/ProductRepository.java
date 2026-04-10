package org.product.domain.repository;

import org.product.domain.entity.Product;

public interface ProductRepository {

    Product save(Product product);
}
