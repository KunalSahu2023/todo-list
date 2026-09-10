package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = "todo.notification.queue")
    public void receiveNotification(String message) {

        System.out.println(
                "NOTIFICATION QUEUE received: " + message
        );
    }
}