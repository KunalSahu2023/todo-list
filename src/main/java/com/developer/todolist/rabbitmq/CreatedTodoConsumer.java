package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CreatedTodoConsumer {

    @RabbitListener(queues = "todo.created.queue")
    public void receiveCreatedMessage(String message) {

        System.out.println(
                "todo.created.queue received: " + message
        );
    }
}