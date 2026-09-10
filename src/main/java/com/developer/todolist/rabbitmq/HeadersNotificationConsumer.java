package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HeadersNotificationConsumer {

    @RabbitListener(
            queues = "todo.headers.notification.queue"
    )
    public void receiveMessage(String message) {

        System.out.println(
                "HEADERS notification queue received: "
                        + message
        );
    }
}