package com.developer.todolist.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.secret}")
    private String secret;

    private static final String PREFIX= "blacklist:";

    public void blackListToken(String token, long expirationMillis){
        String key= PREFIX + token;

        redisTemplate.opsForValue().set(key, true, expirationMillis, TimeUnit.MILLISECONDS);
    }


    public boolean isBlackListed(String token){

        String key= PREFIX + token;

        return Boolean.TRUE.equals(redisTemplate.opsForValue().get(key));

    }

    public void logout(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(
                        io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                                secret.getBytes()
                        )
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Date expiration = claims.getExpiration();

        long remainingTime = expiration.getTime() - System.currentTimeMillis();

        if (remainingTime > 0) {
            blackListToken(token, remainingTime);
        }
    }
}
