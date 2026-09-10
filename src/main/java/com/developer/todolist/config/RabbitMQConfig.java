package com.developer.todolist.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "todo.exchange";

    public static final String TODO_QUEUE = "todo.queue";
    public static final String CREATED_QUEUE = "todo.created.queue";

    public static final String TODO_ROUTING_KEY = "todo";
    public static final String CREATED_ROUTING_KEY = "todo.created";

    @Bean
    public DirectExchange todoExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue todoQueue() {
        return new Queue(TODO_QUEUE);
    }

    @Bean
    public Queue createdQueue() {
        return new Queue(CREATED_QUEUE);
    }

    @Bean
    public Binding todoBinding(
            Queue todoQueue,
            DirectExchange todoExchange
    ) {
        return BindingBuilder
                .bind(todoQueue)
                .to(todoExchange)
                .with(TODO_ROUTING_KEY);
    }

    @Bean
    public Binding createdBinding(
            Queue createdQueue,
            DirectExchange todoExchange
    ) {
        return BindingBuilder
                .bind(createdQueue)
                .to(todoExchange)
                .with(CREATED_ROUTING_KEY);
    }
}