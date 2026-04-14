package org.order.domain.policy;

import org.order.domain.entity.OrderStatus;

public interface OrderStatusPolicy {

    void validateCancel(OrderStatus current);
    void validateConfirm(OrderStatus current);
    void validateComplete(OrderStatus current);

}
