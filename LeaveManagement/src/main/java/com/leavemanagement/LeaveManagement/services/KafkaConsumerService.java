package com.leavemanagement.LeaveManagement.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "leave-email-topic", groupId = "email-group")
    public void listen(String messageJson) {
        System.out.println(">>> Kafka message received: "+messageJson);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> data = objectMapper.readValue(messageJson, new TypeReference<>() {});
            String to = data.get("to");
            String subject = data.get("subject");
            String body = data.get("body");
            System.out.println(">>> Extracted Email Fields:");
            System.out.println("To: " + to);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);

            emailService.sendEmail(to, subject, body);
        } catch (Exception e) {
            System.err.println(">>> Error processing email event: " + e.getMessage());
        }
    }
}