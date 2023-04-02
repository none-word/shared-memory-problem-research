package com.example.sharedmemoryproblemresearch.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimerConfiguration {
    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public Timer simulationTimer(MeterRegistry registry) {
        return Timer.builder("simulation")
                .description("Simulation timer")
                .register(registry);
    }
}
