package com.example.sharedmemoryproblemresearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableCaching
@SpringBootApplication
public class SharedMemoryProblemResearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedMemoryProblemResearchApplication.class, args);
    }
}
