package com.fooddelivery.orders.repositories;

import com.fooddelivery.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
}
