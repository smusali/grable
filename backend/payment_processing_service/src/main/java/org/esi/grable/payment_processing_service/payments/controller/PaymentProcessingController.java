package org.esi.grable.payment_processing_service.payments.controller;

import org.esi.grable.payment_processing_service.payments.model.Payment;
import org.esi.grable.payment_processing_service.payments.model.PaymentInititationInfo;
import org.esi.grable.payment_processing_service.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payments")
@Tag(name = "Payments", description = "CRUD Operations for Payments")
public class PaymentProcessingController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Payment> doPayment(@RequestBody PaymentInititationInfo payment) {
        return ResponseEntity.ok(paymentService.doPayment(payment));
    }
}