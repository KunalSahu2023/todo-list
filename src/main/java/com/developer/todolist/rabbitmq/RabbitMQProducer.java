package com.developer.todolist.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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

        //topic exchange
        public void sendTopicMessage(
                String routingKey,
                String message
        ) {

            rabbitTemplate.convertAndSend(
                    "todo.topic.exchange",
                    routingKey,
                    message
            );

            System.out.println(
                    "Topic message sent | routingKey="
                            + routingKey
                            + " | message="
                            + message
            );
        }

        //header exchange
        public void sendHeadersMessage(
                String message,
                String type,
                String priority
        ) {

            Message rabbitMessage =
                    MessageBuilder
                            .withBody(
                                    message.getBytes(StandardCharsets.UTF_8)
                            )
                            .setHeader("type", type)
                            .setHeader("priority", priority)
                            .build();

            rabbitTemplate.send(
                    "todo.headers.exchange",
                    "",
                    rabbitMessage
            );

            System.out.println(
                    "Headers message sent | "
                            + "type=" + type
                            + " | priority=" + priority
                            + " | message=" + message
            );
        }

        //serialization
        public void sendTodoObject() {

            TodoMessage todoMessage =
                    new TodoMessage(
                            101L,
                            "Learn RabbitMQ",
                            false
                    );

            rabbitTemplate.convertAndSend(
                    "todo.topic.exchange",
                    "todo.created",
                    todoMessage
            );

            System.out.println(
                    "Todo object sent: " + todoMessage
            );
        }
}