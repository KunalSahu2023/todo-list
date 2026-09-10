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

    //direct exchange
    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam(defaultValue = "Hello RabbitMQ") String message
    ) {
        rabbitMQProducer.sendTodoMessage(message);

        return ResponseEntity.ok(
                "Message sent successfully"
        );
    }

    @GetMapping("/send-created")
    public ResponseEntity<String> sendCreatedMessage(
            @RequestParam(defaultValue = "Todo created") String message
    ) {
        rabbitMQProducer.sendCreatedMessage(message);

        return ResponseEntity.ok(
                "Created message sent successfully"
        );
    }

//    fanout exchange
@GetMapping("/send-fanout")
public ResponseEntity<String> sendFanoutMessage(
        @RequestParam(
                defaultValue = "Todo created"
        ) String message
) {
    rabbitMQProducer.sendFanoutMessage(message);

    return ResponseEntity.ok(
            "Fanout message sent successfully"
    );
    }

}
