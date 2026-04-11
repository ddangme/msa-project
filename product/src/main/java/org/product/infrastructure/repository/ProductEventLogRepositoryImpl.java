package org.product.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.product.domain.entity.ProductEventLog;
import org.product.domain.entity.ProductEventStatus;
import org.product.domain.repository.ProductEventLogRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductEventLogRepositoryImpl implements ProductEventLogRepository {

    private final ProductEventLogJpaRepository productEventLogJpaRepository;

    @Override
    public ProductEventLog save(ProductEventLog productEventLog) {
        return productEventLogJpaRepository.save(productEventLog);
    }

    @Override
    public List<ProductEventLog> findByStatus(ProductEventStatus status) {
        return productEventLogJpaRepository.findByStatus(status);
    }
}
