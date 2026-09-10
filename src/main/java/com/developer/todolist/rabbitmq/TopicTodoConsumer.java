package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TopicTodoConsumer {

    @RabbitListener(queues = "todo.topic.queue")
    public void receiveTodoObject(TodoMessage todoMessage) {

        System.out.println(
                "CONSUMER 1 | todo.* | " +
                        "ID=" + todoMessage.getId() +
                        " | Title=" + todoMessage.getTitle() +
                        " | Completed=" + todoMessage.isCompleted()
        );
    }
}