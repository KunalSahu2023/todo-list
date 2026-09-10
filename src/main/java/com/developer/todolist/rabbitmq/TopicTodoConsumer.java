package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TopicTodoConsumer {

    // todo.*- one word
    @RabbitListener(queues = "todo.topic.queue")
    public void receiveMessage(String message) {

        System.out.println(
                "TOPIC todo.* QUEUE received: " + message
        );
    }
}