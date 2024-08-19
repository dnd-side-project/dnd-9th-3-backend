package com.dnd.gooding.springconfig.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.sentinel.master}")
    private String master;

    @Value("${spring.redis.sentinel.password}")
    private String sentinelPassword;

    @Value("${spring.redis.sentinel.nodes:#{null}}")
    private String nodes;

    @Bean
    public RedisTemplate<?, ?> redisCacheTemplate(
            @Qualifier("sentinelConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory,
            @Qualifier("stringRedisSerializer") StringRedisSerializer stringRedisSerializer,
            @Qualifier("jsonSerializer") GenericJackson2JsonRedisSerializer jsonSerializer) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }

    private LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean(name = "sentinelConnectionFactory")
    public LettuceConnectionFactory sentinelConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration().master(master);
        if (nodes != null && !nodes.isEmpty()) {
            String[] sentinel = nodes.split(",");
            for (String node : sentinel) {
                String[] s = node.split(":");
                String host = s[0].trim();
                int port = Integer.parseInt(s[1].trim());
                redisSentinelConfiguration.sentinel(host, port);
            }
            redisSentinelConfiguration.setSentinelPassword(sentinelPassword);
            redisSentinelConfiguration.setPassword(password);
        }
        return new LettuceConnectionFactory(redisSentinelConfiguration);
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
