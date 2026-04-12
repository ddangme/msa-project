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
import org.product.domain.exception.ProductNotFoundException;
import org.product.domain.repository.ProductEventLogRepository;
import org.product.domain.repository.ProductRepository;
import org.product.global.exception.ProductErrorCode;
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
        return ProductInfo.from(getProduct(productId));
    }

    @Transactional
    public void processOrderEvent(OrderEventPayload payload) {
        ProductEventType eventType;
        try {
            Product product = getProduct(payload.productId());
            product.decreaseStock(payload.quantity());
            eventType = ProductEventType.STOCK_DEDUCTED_SUCCESS;
        } catch (Exception e) {
            log.error("주문 재고 차감 실패 - OrderID: {}: {}", payload.orderId(), e.getMessage(), e);
            eventType = ProductEventType.STOCK_DEDUCTED_FAILED;
        }

        saveProductEvent(payload.orderId(), payload.productId(), eventType);
    }

    @Transactional
    public void processOrderCancelled(OrderEventPayload payload) {
        try {
            Product product = getProduct(payload.productId());
            product.restoreStock(payload.quantity());
            log.info("주문 취소로 인한 재고 복구 완료 - OrderID: {}, ProductID: {}", payload.orderId(), payload.productId());
        } catch (Exception e) {
            log.error("재고 복구 실패 - OrderID: {}: {}", payload.orderId(), e.getMessage(), e);
        }
    }

    private void saveProductEvent(UUID orderId, UUID productId, ProductEventType eventType) {
        ProductEventPayload payload = new ProductEventPayload(orderId, productId, eventType);
        try {
            String serializedPayload = objectMapper.writeValueAsString(payload);
            productEventLogRepository.save(ProductEventLog.create(orderId, eventType, serializedPayload));
        } catch (JsonProcessingException e) {
            log.error("상품 이벤트 메시지 직렬화 실패: {}", e.getMessage(), e);
        }
    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND));
    }
}
