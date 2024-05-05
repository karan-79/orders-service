package com.fooddelivery.orders.service;

import com.fooddelivery.orders.api.web.v1.model.APIOrder;
import com.fooddelivery.orders.domain.Order;
import com.fooddelivery.orders.domain.OrderItem;
import com.fooddelivery.orders.repositories.OrdersRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final EntityManager entityManager;

    private final OrdersRepository repository;
    public List<APIOrder> getAllOrders(UUID userId) {
        var ordersByUser = repository.findByUserId(userId);
        return ordersByUser.stream().map(APIOrder::new).toList();
    }

    public APIOrder create(UUID userId, UUID store) {
        var orderID = randomUUID();
        var items = List.of(
          new OrderItem(23L, 32L,  BigDecimal.valueOf(2.33), 3), new OrderItem(22L, 31L,  BigDecimal.valueOf(2.33), 3),
                new OrderItem(21L, 36L,   BigDecimal.valueOf(2.33), 3),
                new OrderItem(20L, 35L,   BigDecimal.valueOf(2.33), 3)
        );
        var ord = new Order(orderID, userId, items, true, store, BigDecimal.valueOf(44.3), 2, Instant.now(), Instant.now());
        return new APIOrder(repository.save(ord));
    }

    public List<Order> placeBatch(List<Order> orders) {
        return repository.saveAll(orders);
    }
}
