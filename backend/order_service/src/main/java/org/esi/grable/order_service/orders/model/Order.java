package org.esi.grable.order_service.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;

    @NotNull(message = "Restaurant id cannot be null")
    private Long restaurantId;

    @NotNull(message = "Table id cannot be null")
    private Long tableId;

    private Instant timestamp;
}