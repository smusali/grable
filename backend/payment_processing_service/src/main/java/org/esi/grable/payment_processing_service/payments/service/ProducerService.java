package org.esi.grable.payment_processing_service.payments.service;

import org.esi.grable.payment_processing_service.payments.config.KafkaTopicConfiguration;
import org.esi.grable.payment_processing_service.payments.model.Payment;
import org.esi.grable.payment_processing_service.payments.model.PaymentDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, PaymentDTO> jsonKafkaTemplate;

    public void sendJsonToPaymentProcessedTopic(PaymentDTO payment) {
        log.info("Log message - Send payment json object to payment topic for order: {} ", payment.getOrderId());
        jsonKafkaTemplate.send(KafkaTopicConfiguration.PAYMENT_PROCESSED_JSON_TOPIC_NAME, payment);
    }
}
