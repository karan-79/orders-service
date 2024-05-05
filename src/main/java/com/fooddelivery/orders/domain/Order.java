package com.fooddelivery.orders.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;
    UUID userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    List<OrderItem> orderItems;
    boolean completed;
    UUID storeId;
    // Set<Offers> appliedOffers; // we'll extend this later
    BigDecimal amount; // will create an invoice entity for now just keep total

    int deliveryAddressId; // comes from users addresses

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    Instant createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    Instant deliveredAt;
}
