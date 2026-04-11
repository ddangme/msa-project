package org.order.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.order.domain.event.OrderEventType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_order_event_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderEventLog {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    @Column(nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderEventType orderEventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderEventStatus status;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int retryCount = 0;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private OrderEventLog(UUID orderId, OrderEventType orderEventType, String payload) {
        this.orderId = orderId;
        this.orderEventType = orderEventType;
        this.payload = payload;
        this.status = OrderEventStatus.INIT;
        this.retryCount = 0;
    }

    public static OrderEventLog create(UUID orderId, String payloadJson) {
        return OrderEventLog.builder()
                .orderId(orderId)
                .orderEventType(OrderEventType.ORDER_CREATED)
                .payload(payloadJson)
                .build();
    }

    public void markAsPublishing() {
        this.status = OrderEventStatus.PUBLISHING;
    }

    public void markAsPublish() {
        this.status = OrderEventStatus.PUBLISHED;
    }

    public void increaseRetryCount(int maxRetry) {
        this.retryCount++;
        if (this.retryCount >= maxRetry) {
            this.status = OrderEventStatus.FAILED;
        } else {
            this.status = OrderEventStatus.INIT;
        }
    }
}