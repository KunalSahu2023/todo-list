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

        //topic exchange
        @GetMapping("/send-topic")
        public ResponseEntity<String> sendTopicMessage(
                @RequestParam(defaultValue = "todo.created") String routingKey,
                @RequestParam(defaultValue = "Todo created") String message
        ) {

            rabbitMQProducer.sendTopicMessage(
                    routingKey,
                    message
            );

            return ResponseEntity.ok(
                    "Topic message sent successfully"
            );
        }

        //header exchange
        @GetMapping("/send-headers")
        public ResponseEntity<String> sendHeadersMessage(
                @RequestParam(defaultValue = "Notification message")
                String message,

                @RequestParam(defaultValue = "notification")
                String type,

                @RequestParam(defaultValue = "high")
                String priority
        ) {

            rabbitMQProducer.sendHeadersMessage(
                    message,
                    type,
                    priority
            );

            return ResponseEntity.ok(
                    "Headers message sent successfully"
            );
        }

        //serialization
        @GetMapping("/send-object")
        public ResponseEntity<String> sendTodoObject() {

            rabbitMQProducer.sendTodoObject();

            return ResponseEntity.ok(
                    "Todo object sent successfully"
            );
        }

}
