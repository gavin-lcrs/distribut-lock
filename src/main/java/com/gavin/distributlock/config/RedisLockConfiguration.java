package com.gavin.distributlock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @Author jiwen.cao
 * @Date 2021/6/18
 * @Description
 */
@Configuration
public class RedisLockConfiguration {

    private static final String registryKey = "gavin";
    private static final long expireAfter = 10 * 1000L;

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, registryKey, expireAfter);
    }
}
