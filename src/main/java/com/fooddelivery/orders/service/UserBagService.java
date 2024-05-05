package com.fooddelivery.orders.service;

import com.fooddelivery.orders.api.web.v1.model.APIBag;
import com.fooddelivery.orders.api.web.v1.model.APIBagItem;
import com.fooddelivery.orders.api.web.v1.model.APICheckoutDetails;
import com.fooddelivery.orders.api.web.v1.model.APIOrder;
import com.fooddelivery.orders.domain.Order;
import com.fooddelivery.orders.domain.OrderItem;
import com.fooddelivery.orders.domain.UserBagItem;
import com.fooddelivery.orders.repositories.UserBagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBagService {

    private final OrderService orderService;

    private final UserBagRepository userBagRepository;

    public APIBag getUserBag(UUID userId) {
        var items = userBagRepository.findByUserId(userId);
        return new APIBag(items.stream().map(APIBagItem::new).collect(Collectors.toSet()));
    }

    public APIBagItem insertItem(UUID userId, APIBagItem item) {
        var userBagItem = new UserBagItem();
        userBagItem.setUserId(userId);
        userBagItem.setStoreId(item.storeId());
        userBagItem.setItemId(item.itemId());
        userBagItem.setQuantity(item.quantity());

        return new APIBagItem(userBagRepository.save(userBagItem));
    }

    public void updateItem(APIBagItem item, UUID userId) {
        Objects.requireNonNull(item.id());

        var userBagItem = new UserBagItem();
        userBagItem.setId(item.id());
        userBagItem.setUserId(userId);
        userBagItem.setStoreId(item.storeId());
        userBagItem.setItemId(item.itemId());
        userBagItem.setQuantity(item.quantity());

        userBagRepository.save(userBagItem);
    }

    public List<APIOrder> checkout(UUID userId, APICheckoutDetails checkoutDetails) {
        // placeOrders
        // skipping payments

        var itemsToBeOrdered = getUserBag(userId).bagItems();

        var itemsPerStore = getItemsPerStore(itemsToBeOrdered);

        var orders = itemsPerStore.entrySet().stream()
                .map(mapItemsToOrders(userId, checkoutDetails)).toList();

        return orderService.placeBatch(orders).stream().map(APIOrder::new).toList();
    }

    private Function<Map.Entry<UUID, Set<APIBagItem>>, Order> mapItemsToOrders(UUID userId, APICheckoutDetails checkoutDetails) {
        return map -> {
            var storeId = map.getKey();
            var items = map.getValue();
//            var itemWithPrice = storeClient.getItemsPricesForUser(items, userId)

            var orderItems = getOrderItemsFromBagItems(items);
            var totalAmount = getTotalAmount(orderItems);
            // getAppliedOffers
            // deduct from total
            var order = new Order();
            order.setStoreId(storeId);
            order.setUserId(userId);
            order.setCompleted(false);
            order.setAmount(totalAmount);
            order.setDeliveryAddressId(checkoutDetails.addressId());
            return order;
        };
    }

    private HashMap<UUID, Set<APIBagItem>> getItemsPerStore(Set<APIBagItem> itemsToBeOrdered) {
        return itemsToBeOrdered.stream()
                .reduce(new HashMap<UUID, Set<APIBagItem>>(),
                        (acc, bagItem) -> {
                            if (acc.containsKey(bagItem.storeId())) {
                                acc.get(bagItem.storeId()).add(bagItem);
                                return acc;
                            }
                            var set = new HashSet<APIBagItem>();
                            set.add(bagItem);

                            acc.put(bagItem.storeId(), set);
                            return acc;
                        },
                        (a, b) -> {
                            a.putAll(b);
                            return a;
                        });
    }

    private BigDecimal getTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream().reduce(new BigDecimal(0),
                (ammount, item) -> {
                    return ammount.add(item.getPrice());
                }, BigDecimal::add);
    }

    private List<OrderItem> getOrderItemsFromBagItems(Set<APIBagItem> items) {
        return items.stream().map(item -> {
            var orderItem = new OrderItem();
            orderItem.setItemId(item.itemId());
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(BigDecimal.valueOf(23.2)); //mock for now
            return orderItem;
        }).toList();
    }
}
