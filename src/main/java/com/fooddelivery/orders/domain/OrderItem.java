package com.fooddelivery.orders.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    public OrderItem(long id, long itemId, BigDecimal price, int quantity) {
        this.id = id;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long itemId; //item id from a certain store
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order orderId;
    BigDecimal price;
    int quantity;
}
