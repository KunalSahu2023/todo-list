package com.developer.todolist.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConnectionTest {

    @Bean
    CommandLineRunner testRabbitMQConnection(RabbitTemplate rabbitTemplate) {
        return args -> {
            rabbitTemplate.execute(channel -> {
                System.out.println("======================================");
                System.out.println("RabbitMQ connection successful!");
                System.out.println("RabbitMQ connection: " +
                        channel.getConnection().getAddress());
                System.out.println("======================================");
                return null;
            });
        };
    }
}

