package org.esi.grable.payment_processing_service.payments.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    public static final String PAYMENT_PROCESSED_JSON_TOPIC_NAME = "PaymentProcessedJson";

    @Bean
    public NewTopic PaymentProcessedJson() {
        return TopicBuilder.name(PAYMENT_PROCESSED_JSON_TOPIC_NAME)
                .build();
    }
}