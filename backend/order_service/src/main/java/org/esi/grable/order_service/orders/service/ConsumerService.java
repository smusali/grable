package org.esi.grable.order_service.orders.service;

import org.esi.grable.order_service.orders.model.Order;
import org.esi.grable.order_service.orders.model.OrderStatus;
import org.esi.grable.order_service.orders.model.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ConsumerService {
    @Autowired
    private OrderService orderService;
    public static final String PAYMENT_PROCESSED_JSON_TOPIC_NAME = "PaymentProcessedJson";

    @KafkaListener(topics = PAYMENT_PROCESSED_JSON_TOPIC_NAME, groupId = "orderEventGroup")
    public void consumes(PaymentDTO paymentDTO) {
        log.info("Log message recieved from paymentDTO topic: {} ", paymentDTO.toString());
        Order order = orderService.getOrder(paymentDTO.getOrderId());
        order.setStatus(OrderStatus.PAID);
        orderService.updateOrder(paymentDTO.getOrderId(), order);

    }
}

