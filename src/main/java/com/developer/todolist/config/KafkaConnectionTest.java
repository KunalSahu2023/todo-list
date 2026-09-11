package com.developer.todolist.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConnectionTest {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    CommandLineRunner testKafkaConnection() {

        return args -> {

            Properties properties = new Properties();

            properties.put(
                    AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                    bootstrapServers
            );

            try (AdminClient adminClient =
                         AdminClient.create(properties)) {

                adminClient
                        .listTopics()
                        .names()
                        .get();

                System.out.println(
                        "======================================"
                );
                System.out.println(
                        "Kafka connection successful!"
                );
                System.out.println(
                        "Kafka bootstrap server: "
                                + bootstrapServers
                );
                System.out.println(
                        "======================================"
                );
            }
        };
    }
}