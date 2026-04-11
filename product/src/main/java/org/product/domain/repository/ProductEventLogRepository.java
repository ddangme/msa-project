package org.product.domain.repository;

import org.product.domain.entity.ProductEventLog;
import org.product.domain.entity.ProductEventStatus;

import java.util.List;

public interface ProductEventLogRepository {
    ProductEventLog save(ProductEventLog productEventLog);
    List<ProductEventLog> findByStatus(ProductEventStatus status);
}
