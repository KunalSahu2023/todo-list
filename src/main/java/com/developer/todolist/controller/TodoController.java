package com.developer.todolist.controller;

import com.developer.todolist.kafka.TodoProducer;
import com.developer.todolist.model.TodoRequest;
import com.developer.todolist.model.TodoResponse;
import com.developer.todolist.redis.TodoEventPublisher;
import com.developer.todolist.service.DistributedLockService;
import com.developer.todolist.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private final DistributedLockService distributedLockService;

    private final TodoProducer todoProducer;

    private final TodoEventPublisher todoEventPublisher;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest request,
            Authentication authentication
    ) {

        TodoResponse response =
                todoService.createTodo(
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TodoResponse>> getTodos(
            Authentication authentication,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            Boolean completed,

            @RequestParam(required = false)
            String search
    ) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page must be greater than or equal to 0"
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Size must be between 1 and 100"
            );
        }

        Page<TodoResponse> response =
                todoService.getTodos(
                        authentication.getName(),
                        page,
                        size,
                        completed,
                        search
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @PathVariable Long id,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                todoService.getTodoById(
                        id,
                        authentication.getName()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                todoService.updateTodo(
                        id,
                        request,
                        authentication.getName()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            Authentication authentication
    ) {

        todoService.deleteTodo(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lock-test")
    public ResponseEntity<String> lockTest() {

        String lockKey = "lock:test";

        String lockValue =
                distributedLockService.tryLock(
                        lockKey,
                        30
                );

        if (lockValue == null) {
            return ResponseEntity.status(409)
                    .body("Lock already acquired");
        }

        try {

            return ResponseEntity.ok(
                    "Lock acquired. Owner: " + lockValue
            );

        } finally {

            distributedLockService.releaseLock(
                    lockKey,
                    lockValue
            );
        }
    }

    @GetMapping("/lock-owner-test")
    public ResponseEntity<String> lockOwnerTest(
            @RequestParam String owner
    ) {

        String lockKey = "lock:owner-test";

        if (owner.equals("A")) {

            boolean released =
                    distributedLockService.releaseLock(
                            lockKey,
                            "owner-A"
                    );

            return ResponseEntity.ok(
                    "Owner A release result: " + released
            );
        }

        if (owner.equals("B")) {

            boolean released =
                    distributedLockService.releaseLock(
                            lockKey,
                            "owner-B"
                    );

            return ResponseEntity.ok(
                    "Owner B release result: " + released
            );
        }

        return ResponseEntity.badRequest()
                .body("Use owner=A or owner=B");
    }

    @GetMapping("/lock-concurrent-test")
    public ResponseEntity<String> lockConcurrentTest() {

        String lockKey = "lock:concurrent-test";

        String lockValue =
                distributedLockService.tryLock(
                        lockKey,
                        30
                );

        if (lockValue == null) {
            return ResponseEntity.status(409)
                    .body("Could not acquire lock");
        }

        try {

            System.out.println(
                    "LOCK ACQUIRED: " + lockValue
            );

            Thread.sleep(10_000);

            return ResponseEntity.ok(
                    "Lock acquired and work completed"
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return ResponseEntity.internalServerError()
                    .body("Thread interrupted");

        } finally {

            boolean released =
                    distributedLockService.releaseLock(
                            lockKey,
                            lockValue
                    );

            System.out.println(
                    "LOCK RELEASED: " + released
            );
        }
    }

    @GetMapping("/redis-pubsub-test")
    public ResponseEntity<String> redisPubSubTest() {

        todoEventPublisher.publish(
                "Hello Redis Pub/Sub!"
        );

        return ResponseEntity.ok(
                "Redis Pub/Sub message published"
        );
    }

    @GetMapping("/kafka-test")
    public ResponseEntity<String> kafkaTest() {

        todoProducer.sendMessage("Hello Kafka!");

        return ResponseEntity.ok(
                "Message sent to Kafka"
        );
    }

}
