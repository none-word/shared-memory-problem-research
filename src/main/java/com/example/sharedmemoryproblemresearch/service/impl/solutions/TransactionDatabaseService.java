package com.example.sharedmemoryproblemresearch.service.impl.solutions;

import com.example.sharedmemoryproblemresearch.exception.NotFoundRandomRecordException;
import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.updateDataEntity;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "solution.type", havingValue = "TRANSACTION")
@Service
public class TransactionDatabaseService implements DatabaseService {
    private final DatabaseRepository databaseRepository;

    @Value("${length.text}")
    private Integer length;

    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public void changeData() {
        logRetry();
        DataEntity dataEntity = databaseRepository.findRandomRecord().orElseThrow(NotFoundRandomRecordException::new);
        updateDataEntity(dataEntity, length);
        databaseRepository.saveAndFlush(dataEntity);
        log.info("[Transactional] Changed entity with uuid: {}", dataEntity.getUuid());
    }

    private static void logRetry() {
        if (RetrySynchronizationManager.getContext().getRetryCount() > 0) {
            log.warn("[Transactional] One more attempt");
        }
    }
}
