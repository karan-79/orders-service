package com.fooddelivery.orders.api.web.v1.model;

import com.fooddelivery.orders.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record APIOrder(
        UUID id,
        UUID storeId,
        BigDecimal totalAmount,
        List<APIOrderItem> items,
        boolean completed
) {
    public APIOrder(Order order) {
        this(order.getOrderId(), order.getStoreId(), order.getAmount(),
                order.getOrderItems().stream().map(APIOrderItem::new).toList(),
                order.isCompleted());
    }
}
