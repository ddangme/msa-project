package org.product.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.product.application.dto.CreateProductCommand;
import org.product.domain.event.OrderEventPayload;
import org.product.domain.event.ProductEventPayload;
import org.product.application.dto.ProductInfo;
import org.product.domain.entity.Product;
import org.product.domain.entity.ProductEventLog;
import org.product.domain.event.ProductEventType;
import org.product.domain.repository.ProductEventLogRepository;
import org.product.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductEventLogRepository productEventLogRepository;
    private final ObjectMapper objectMapper;

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

    @Transactional
    public void processOrderEvent(OrderEventPayload payload) {
        ProductEventType eventType;
        try {
            Product product = productRepository.findById(payload.productId());
            product.decreaseStock(payload.quantity());
            eventType = ProductEventType.STOCK_DEDUCTED_SUCCESS;
        } catch (Exception e) {
            log.error("Stock deduction failed for order {}: {}", payload.orderId(), e.getMessage(), e);
            eventType = ProductEventType.STOCK_DEDUCTED_FAILED;
        }

        saveProductEvent(payload.orderId(), payload.productId(), eventType);
    }

    private void saveProductEvent(UUID orderId, UUID productId, ProductEventType eventType) {
        ProductEventPayload payload = new ProductEventPayload(orderId, productId, eventType);
        try {
            String serializedPayload = objectMapper.writeValueAsString(payload);
            productEventLogRepository.save(ProductEventLog.create(orderId, eventType, serializedPayload));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize product event payload: {}", e.getMessage(), e);
        }
    }
}
