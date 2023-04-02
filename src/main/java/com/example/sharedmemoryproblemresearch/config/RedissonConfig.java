package com.example.sharedmemoryproblemresearch.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${redis.address}")
    private String redisAddress;
    @Value("${spring.data.redis.password}")
    private String redisPassword;
    @Value("${redis.name}")
    private String redisName;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisAddress)
                .setPassword(redisPassword)
                .setUsername(redisName);
        return Redisson.create(config);
    }
}
