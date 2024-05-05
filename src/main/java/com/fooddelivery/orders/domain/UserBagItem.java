package com.fooddelivery.orders.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBagItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID id;

    UUID userId;

    UUID storeId;

    long itemId;

    int quantity;

}
