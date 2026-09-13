package com.developer.todolist.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoEventPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CHANNEL = "todo-events";

    public void publish(String message) {

        redisTemplate.convertAndSend(
                CHANNEL,
                message
        );

        System.out.println("======================================");
        System.out.println("Redis Pub/Sub message published!");
        System.out.println("Channel: " + CHANNEL);
        System.out.println("Message: " + message);
        System.out.println("======================================");
    }
}