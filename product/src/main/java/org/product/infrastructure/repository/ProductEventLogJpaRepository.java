package org.product.infrastructure.repository;

import org.product.domain.entity.ProductEventLog;
import org.product.domain.entity.ProductEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductEventLogJpaRepository extends JpaRepository<ProductEventLog, UUID> {
    List<ProductEventLog> findByStatus(ProductEventStatus status);
}
