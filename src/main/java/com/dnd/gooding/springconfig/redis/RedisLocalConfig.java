package com.dnd.gooding.springconfig.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile("local")
@Configuration
public class RedisLocalConfig {

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedisTemplate<?, ?> redisCacheTemplate(
            @Qualifier("standaloneConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory,
            @Qualifier("stringRedisSerializer") StringRedisSerializer stringRedisSerializer,
            @Qualifier("jsonSerializer") GenericJackson2JsonRedisSerializer jsonSerializer) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }

    @Bean(name = "standaloneConnectionFactory")
    public LettuceConnectionFactory standaloneConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(standaloneConfiguration);
    }

    @Bean(name = "jsonSerializer")
    public GenericJackson2JsonRedisSerializer jsonSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean(name = "stringRedisSerializer")
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }
}
