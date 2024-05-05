package com.fooddelivery.orders.api.web.v1.model;

import com.fooddelivery.orders.domain.OrderItem;

import java.math.BigDecimal;
import java.util.UUID;

public record APIOrderItem (
        long id,
        long itemId,
        int quantity,
        BigDecimal price
) {
    public APIOrderItem(OrderItem orderItem) {
        this(orderItem.getId(), orderItem.getItemId(), orderItem.getQuantity(), orderItem.getPrice());
    }
}
