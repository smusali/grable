package org.esi.grable.payment_processing_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentProcessingApplication.class, args);
    }

}