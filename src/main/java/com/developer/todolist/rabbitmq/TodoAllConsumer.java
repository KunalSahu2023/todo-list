package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TodoAllConsumer {

    //todo.#- zero or more words
    @RabbitListener(queues = "todo.all.queue")
    public void receiveMessage(String message) {

        System.out.println(
                "TOPIC todo.# QUEUE received: " + message
        );
    }
}