package dev.mfikri.weather.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class RedisConfig {


//    @Autowired
//    public  RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
//
//    }

//    public RedisTemplate<String, Object> redisTemplate () {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }
}
