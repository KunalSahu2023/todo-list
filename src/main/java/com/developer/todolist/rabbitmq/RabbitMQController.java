package com.developer.todolist.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@RequiredArgsConstructor
public class RabbitMQController {

    private final RabbitMQProducer rabbitMQProducer;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam(defaultValue = "Hello RabbitMQ") String message
    ) {
        rabbitMQProducer.sendMessage(message);

        return ResponseEntity.ok("Message sent successfully");
    }
}
