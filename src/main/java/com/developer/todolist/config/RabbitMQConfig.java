package com.developer.todolist.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "todo.queue";

    @Bean
    public Queue todoQueue() {
        return new Queue(QUEUE_NAME);
    }
}
