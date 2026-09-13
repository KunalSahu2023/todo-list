package com.developer.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DistributedLockService {

    private final StringRedisTemplate redisTemplate;

    public String tryLock(
            String lockKey,
            long expirationSeconds
    ) {

        String lockValue = UUID.randomUUID().toString();

        Boolean acquired =
                redisTemplate.opsForValue().setIfAbsent(
                        lockKey,
                        lockValue,
                        expirationSeconds,
                        TimeUnit.SECONDS
                );

        if (Boolean.TRUE.equals(acquired)) {
            return lockValue;
        }

        return null;
    }

    public boolean releaseLock(
            String lockKey,
            String lockValue
    ) {

        String script =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "return redis.call('del', KEYS[1]) " +
                        "else " +
                        "return 0 " +
                        "end";

        DefaultRedisScript<Long> redisScript =
                new DefaultRedisScript<>(
                        script,
                        Long.class
                );

        Long result =
                redisTemplate.execute(
                        redisScript,
                        Collections.singletonList(lockKey),
                        lockValue
                );

        return Long.valueOf(1).equals(result);
    }
}