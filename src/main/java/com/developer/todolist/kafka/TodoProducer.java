package com.developer.todolist.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {

        try {

            kafkaTemplate.send(
                    "todo-events",
                    message
            ).get();

            System.out.println("======================================");
            System.out.println("Kafka message sent successfully!");
            System.out.println("Message: " + message);
            System.out.println("======================================");

        } catch (Exception e) {

            System.out.println("======================================");
            System.out.println("Kafka message FAILED!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("======================================");

            throw new RuntimeException(
                    "Failed to send message to Kafka",
                    e
            );
        }
    }
}