 package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "todo.queue")
    public void receiveMessage(String message) {

        System.out.println("Message received: " + message);
    }
}

