package com.developer.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//Maintain a Redis counter and determine whether the request is within the allowed limit.

@Service
@RequiredArgsConstructor
public class RateLimitService {

    @Value("${rate-limit.login.max-requests}")
    private int maxRequests;

    @Value("${rate-limit.login.window-seconds}")
    private long windowSeconds;

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean isAllowed(String key){
        Long count = redisTemplate.opsForValue().increment(key); // increment value work as ip

        if (count!=null && count==1)
            redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS); // expire after 60 seconds

        return count!=null && count <= maxRequests; // allow only 5 requests
    }
}
