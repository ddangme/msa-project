package org.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_product_event_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ProductEventLog {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    @Column(nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductEventType eventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductEventStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private ProductEventLog(UUID orderId, ProductEventType eventType, String payload) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = ProductEventStatus.INIT;
    }

    public static ProductEventLog create(UUID orderId, ProductEventType eventType, String payloadJson) {
        return ProductEventLog.builder()
                .orderId(orderId)
                .eventType(eventType)
                .payload(payloadJson)
                .build();
    }

    public void completePublish() {
        this.status = ProductEventStatus.PUBLISHED;
    }
}
