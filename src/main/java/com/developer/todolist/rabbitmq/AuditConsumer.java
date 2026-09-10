package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    @RabbitListener(queues = "todo.audit.queue")
    public void receiveAudit(String message) {

        System.out.println(
                "AUDIT QUEUE received: " + message
        );
    }
}