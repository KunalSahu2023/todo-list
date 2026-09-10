package com.developer.todolist.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    //direct exchange

    public void sendTodoMessage(String message) {

        rabbitTemplate.convertAndSend(
                "todo.exchange",
                "todo",
                message
        );

        System.out.println(
                "Sent with routing key: todo"
        );
    }

    public void sendCreatedMessage(String message) {

        rabbitTemplate.convertAndSend(
                "todo.exchange",
                "todo.created",
                message
        );

        System.out.println(
                "Sent with routing key: todo.created"
        );
    }

//    fanout exchange
public void sendFanoutMessage(String message) {

    rabbitTemplate.convertAndSend(
            "todo.fanout.exchange",
            "",
            message
    );

    System.out.println(
            "Fanout message sent: " + message
    );
}
}