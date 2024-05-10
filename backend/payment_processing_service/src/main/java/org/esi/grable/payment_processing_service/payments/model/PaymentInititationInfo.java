package org.esi.grable.payment_processing_service.payments.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentInititationInfo {
    @NotNull(message = "Transaction Tracking Id cannot be null")
    private String transactionTrackingId;

    @NotNull(message = "OrderId cannot be null")
    private Long orderId;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @NotNull(message = "Payment method status cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;
}
