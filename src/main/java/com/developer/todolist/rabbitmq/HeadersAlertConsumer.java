package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HeadersAlertConsumer {

    @RabbitListener(
            queues = "todo.headers.alert.queue"
    )
    public void receiveMessage(String message) {

        System.out.println(
                "HEADERS alert queue received: "
                        + message
        );
    }
}