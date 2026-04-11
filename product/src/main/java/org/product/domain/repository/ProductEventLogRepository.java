package org.product.domain.repository;

import org.product.domain.entity.ProductEventLog;
import org.product.domain.entity.ProductEventStatus;

import java.util.List;
import java.util.UUID;

public interface ProductEventLogRepository {

    ProductEventLog save(ProductEventLog productEventLog);

    List<ProductEventLog> findByStatus(ProductEventStatus status);

    ProductEventLog findById(UUID eventId);
}
