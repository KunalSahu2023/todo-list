package com.developer.todolist.controller;

import com.developer.todolist.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisTestContoller {

    private final RedisTestService redisTestService;

    @PostMapping
    public ResponseEntity<String> setValue(@RequestParam String key, @RequestParam String value){

        redisTestService.setValue(key, value);

        return ResponseEntity.ok("Value stored in Redis");
    }

    @GetMapping

    public ResponseEntity<String> getValue(@RequestParam String key){

        String value= redisTestService.getValue(key);

        if(value== null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(value);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteValue(@RequestParam String key){

        redisTestService.deleteValue(key);

        return ResponseEntity.ok("Value deleted from Redis");
    }
}
