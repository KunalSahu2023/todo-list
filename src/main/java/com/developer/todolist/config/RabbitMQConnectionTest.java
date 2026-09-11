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

            int maxAttempts = 10;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                try {

                    rabbitTemplate.execute(channel -> {

                        System.out.println("======================================");
                        System.out.println("RabbitMQ connection successful!");
                        System.out.println("RabbitMQ connection: "
                                + channel.getConnection().getAddress());
                        System.out.println("======================================");

                        return null;
                    });

                    // Connection successful, so stop retrying
                    return;

                } catch (Exception e) {

                    System.out.println(
                            "RabbitMQ connection attempt "
                                    + attempt
                                    + " failed."
                    );

                    if (attempt == maxAttempts) {
                        System.out.println(
                                "RabbitMQ is not available. "
                                        + "Application will continue running."
                        );
                        return;
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        };
    }
}
