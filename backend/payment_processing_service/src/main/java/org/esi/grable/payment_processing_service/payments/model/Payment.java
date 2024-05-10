package org.esi.grable.payment_processing_service.payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Transaction Tracking Id cannot be null")
    @Column(unique = true)
    private String transactionTrackingId;

    @NotNull(message = "OrderId cannot be null")
    private Long orderId;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @NotNull(message = "Payment method status cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
}