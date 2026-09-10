package com.developer.todolist.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TodoAllConsumer {

    @RabbitListener(queues = "todo.all.queue")
    public void receiveTodoObject(TodoMessage todoMessage) {

        System.out.println(
                "CONSUMER 2 | todo.# | " +
                        "ID=" + todoMessage.getId() +
                        " | Title=" + todoMessage.getTitle()
                        + " | Completed=" + todoMessage.isCompleted()
        );
    }
}