package com.leavemanagement.LeaveManagement.services;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.leave-email}")
    private String topic;

    public void sendEmailEvent(String emailJson) {
        kafkaTemplate.send(topic, emailJson).whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println(">>> Email event sent to Kafka: " + emailJson);
            } else {
                System.err.println(">>> Failed to send email event: " + ex.getMessage());
            }
        });
    }
}
