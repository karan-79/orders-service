package com.fooddelivery.orders.api.web.v1.model;

import java.util.Set;

public record APIBag(Set<APIBagItem> bagItems) {
}
