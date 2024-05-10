package org.esi.grable.payment_processing_service.payments.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDTO {
    @NotNull(message = "OrderId cannot be null")
    private Long orderId;

    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;
}
