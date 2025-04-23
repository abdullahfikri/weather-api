package dev.mfikri.weather.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mfikri.weather.entity.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


@Component
public class RedisConfig {

    @Bean
    public RedisTemplate<String, WeatherEntity> redisTemplate (RedisConnectionFactory connectionFactory) {
        final Jackson2JsonRedisSerializer<WeatherEntity> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(WeatherEntity.class);

        RedisTemplate<String, WeatherEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }



}
