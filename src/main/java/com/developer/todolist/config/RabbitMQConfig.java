package com.developer.todolist.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {

    //direct exchange
    public static final String EXCHANGE_NAME = "todo.exchange";

    public static final String TODO_QUEUE = "todo.queue";
    public static final String TODO_ROUTING_KEY = "todo";

    public static final String CREATED_QUEUE = "todo.created.queue";
    public static final String CREATED_ROUTING_KEY = "todo.created";

    //fanout exchange
    public static final String FANOUT_EXCHANGE_NAME = "todo.fanout.exchange";
    public static final String NOTIFICATION_QUEUE = "todo.notification.queue";
    public static final String AUDIT_QUEUE = "todo.audit.queue";

    //direct exchange
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

//    fanout exchange
    @Bean
    public FanoutExchange todoFanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE);
    }

    @Bean
    public Binding notificationBinding(
            Queue notificationQueue,
            FanoutExchange todoFanoutExchange
    ) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(todoFanoutExchange);
    }

    @Bean
    public Binding auditBinding(
            Queue auditQueue,
            FanoutExchange todoFanoutExchange
    ) {
        return BindingBuilder
                .bind(auditQueue)
                .to(todoFanoutExchange);
    }

    //topic exchange

    @Bean
    public TopicExchange todoTopicExchange() {
        return new TopicExchange("todo.topic.exchange");
    }

    //todo.* - one word
    @Bean
    public Queue todoTopicQueue() {
        return new Queue("todo.topic.queue");
    }

    @Bean
    public Binding todoTopicBinding(
            Queue todoTopicQueue,
            TopicExchange todoTopicExchange
    ) {
        return BindingBuilder
                .bind(todoTopicQueue)
                .to(todoTopicExchange)
                .with("todo.*");
    }

    //todo.#- zero or more word
    @Bean
    public Queue todoAllQueue() {
        return new Queue("todo.all.queue");
    }

    @Bean
    public Binding todoAllBinding(
            Queue todoAllQueue,
            TopicExchange todoTopicExchange
    ) {
        return BindingBuilder
                .bind(todoAllQueue)
                .to(todoTopicExchange)
                .with("todo.#");
    }

    //header exchange
    @Bean
    public HeadersExchange todoHeadersExchange() {
        return new HeadersExchange("todo.headers.exchange");
    }

    //match-all

    @Bean
    public Queue notificationHeadersQueue() {
        return new Queue("todo.headers.notification.queue");
    }

    @Bean
    public Binding notificationHeadersBinding(
            Queue notificationHeadersQueue,
            HeadersExchange todoHeadersExchange
    ) {
        return BindingBuilder
                .bind(notificationHeadersQueue)
                .to(todoHeadersExchange)
                .whereAll(
                        Map.of(
                                "type", "notification",
                                "priority", "high"
                        )
                )
                .match();
    }

    //match-any
    @Bean
    public Queue alertHeadersQueue() {
        return new Queue("todo.headers.alert.queue");
    }

    @Bean
    public Binding alertHeadersBinding(
            Queue alertHeadersQueue,
            HeadersExchange todoHeadersExchange
    ) {
        return BindingBuilder
                .bind(alertHeadersQueue)
                .to(todoHeadersExchange)
                .whereAny(
                        Map.of(
                                "type", "notification",
                                "priority", "high"
                        )
                )
                .match();
    }

}