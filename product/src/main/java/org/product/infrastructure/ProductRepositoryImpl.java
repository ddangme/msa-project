package org.product.infrastructure;

import lombok.RequiredArgsConstructor;
import org.product.domain.entity.Product;
import org.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
}
