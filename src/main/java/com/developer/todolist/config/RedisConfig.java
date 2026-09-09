package com.developer.todolist.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       ObjectMapper objectMapper){

        RedisTemplate<String, Object> template= new RedisTemplate<>();

        //support date time
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer= new GenericJackson2JsonRedisSerializer(objectMapper);

        template.setConnectionFactory(redisConnectionFactory);

        // key
        template.setKeySerializer(new StringRedisSerializer());

        // value
        template.setValueSerializer(genericJackson2JsonRedisSerializer);

        // hashSet key
        template.setHashKeySerializer(new StringRedisSerializer());

        // hashSet value
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }
}
