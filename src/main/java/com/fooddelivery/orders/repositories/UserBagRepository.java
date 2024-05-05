package com.fooddelivery.orders.repositories;

import com.fooddelivery.orders.domain.UserBagItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserBagRepository extends JpaRepository<UserBagItem, Long> {
    List<UserBagItem> findByUserId(UUID userId);

}
