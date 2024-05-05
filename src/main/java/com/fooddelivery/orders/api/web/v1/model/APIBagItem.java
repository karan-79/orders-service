package com.fooddelivery.orders.api.web.v1.model;

import com.fooddelivery.orders.domain.UserBagItem;

import java.util.UUID;

public record APIBagItem(
        UUID id,
        UUID storeId,
        long itemId,
        int quantity
) {
    public APIBagItem(UserBagItem item) {
        this(item.getId(), item.getStoreId(), item.getItemId(), item.getQuantity());
    }
}
