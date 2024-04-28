package org.esi.grable.order_service.orderDetails.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderDetails")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order id cannot be null")
    private Long orderId;

    @NotNull(message = "Menu item id cannot be null")
    private Long menuItemId;

    @NotNull(message = "Quantity cannot be null")
    private Long quantity;
}