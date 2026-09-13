package com.developer.todolist.config;

import com.developer.todolist.redis.TodoEventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public ChannelTopic todoEventsTopic() {
        return new ChannelTopic("todo-events");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            TodoEventSubscriber subscriber,
            ChannelTopic todoEventsTopic
    ) {

        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(
                subscriber,
                todoEventsTopic
        );

        return container;
    }
}