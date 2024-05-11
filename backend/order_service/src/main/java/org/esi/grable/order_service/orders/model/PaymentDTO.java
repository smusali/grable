package org.esi.grable.order_service.orders.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentDTO {
    @NotNull(message = "OrderId cannot be null")
    private Long orderId;

    @NotNull(message = "Payment status cannot be null")
    private String paymentStatus;
}
