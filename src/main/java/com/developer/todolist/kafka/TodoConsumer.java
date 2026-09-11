package com.developer.todolist.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TodoConsumer {

    @KafkaListener(
            topics = "todo-events",
            groupId = "todo-group"
    )
    public void consumeMessage(String message) {

        System.out.println("======================================");
        System.out.println("Kafka message received!");
        System.out.println("Message: " + message);
        System.out.println("======================================");
    }
}