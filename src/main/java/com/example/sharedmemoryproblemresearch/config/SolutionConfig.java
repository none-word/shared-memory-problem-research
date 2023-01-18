package com.example.sharedmemoryproblemresearch.config;

import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import com.example.sharedmemoryproblemresearch.service.impl.CacheDatabaseService;
import com.example.sharedmemoryproblemresearch.service.impl.LockingDatabaseService;
import com.example.sharedmemoryproblemresearch.service.impl.MessageBrokerDatabaseService;
import com.example.sharedmemoryproblemresearch.service.impl.SimpleDatabaseService;
import com.example.sharedmemoryproblemresearch.service.impl.TransactionDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SolutionConfig {
    private final DatabaseRepository databaseRepository;

    @ConditionalOnProperty(prefix = "solution", name = "type", havingValue = "TRANSACTION")
    @Bean
    public DatabaseService transactionSolution() {
        return new TransactionDatabaseService();
    }

    @ConditionalOnProperty(prefix = "solution", name = "type", havingValue = "LOCKING")
    @Bean
    public DatabaseService lockingSolution() {
        return new LockingDatabaseService();
    }

    @ConditionalOnProperty(prefix = "solution", name = "type", havingValue = "CACHE")
    @Bean
    public DatabaseService cacheSolution() {
        return new CacheDatabaseService();
    }

    @ConditionalOnProperty(prefix = "solution", name = "type", havingValue = "MESSAGE_BROKER")
    @Bean
    public DatabaseService messageBrokerSolution() {
        return new MessageBrokerDatabaseService();
    }

    @ConditionalOnMissingBean
    @Bean
    public DatabaseService withoutSolution() {
        return new SimpleDatabaseService(databaseRepository);
    }
}
