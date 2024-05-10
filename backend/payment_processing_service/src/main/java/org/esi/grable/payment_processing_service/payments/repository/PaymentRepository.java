package org.esi.grable.payment_processing_service.payments.repository;

import org.esi.grable.payment_processing_service.payments.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}