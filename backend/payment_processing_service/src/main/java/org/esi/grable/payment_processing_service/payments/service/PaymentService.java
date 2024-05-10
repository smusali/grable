package org.esi.grable.payment_processing_service.payments.service;

import lombok.extern.slf4j.Slf4j;
import org.esi.grable.payment_processing_service.payments.model.Payment;
import org.esi.grable.payment_processing_service.payments.model.PaymentDTO;
import org.esi.grable.payment_processing_service.payments.model.PaymentInititationInfo;
import org.esi.grable.payment_processing_service.payments.model.PaymentStatus;
import org.esi.grable.payment_processing_service.payments.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProducerService producerService;


    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment doPayment(PaymentInititationInfo paymentInititationInfo) {
        var payment = Payment.builder()
                .paymentStatus(paymentInititationInfo.getPaymentStatus())
                .paymentMethod(paymentInititationInfo.getPaymentMethod())
                .amount(paymentInititationInfo.getAmount())
                .orderId(paymentInititationInfo.getOrderId())
                .transactionDate(LocalDateTime.now())
                .transactionTrackingId(paymentInititationInfo.getTransactionTrackingId())
                .build();
        payment = paymentRepository.save(payment);
        log.info("Payment {} is added to the Database", payment.getId());

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .paymentStatus(PaymentStatus.DONE)
                .orderId(payment.getOrderId()).build();
        producerService.sendJsonToPaymentProcessedTopic(paymentDTO);
        return payment;
    }
}
