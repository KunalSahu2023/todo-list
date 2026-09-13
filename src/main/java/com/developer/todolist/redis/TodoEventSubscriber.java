package com.developer.todolist.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class TodoEventSubscriber implements MessageListener {

    @Override
    public void onMessage(
            Message message,
            byte[] pattern
    ) {

        String receivedMessage =
                new String(
                        message.getBody(),
                        StandardCharsets.UTF_8
                );

        System.out.println("======================================");
        System.out.println("Redis Pub/Sub message received!");
        System.out.println("Channel: " +
                new String(
                        message.getChannel(),
                        StandardCharsets.UTF_8
                ));
        System.out.println("Message: " + receivedMessage);
        System.out.println("======================================");
    }
}