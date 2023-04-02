package com.example.sharedmemoryproblemresearch.service.impl.solutions;

import com.example.sharedmemoryproblemresearch.exception.NotFoundRandomRecordException;
import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.updateDataEntity;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "solution.type", havingValue = "CACHE")
@Service
public class CacheDatabaseService implements DatabaseService {
    private final RedissonClient redisson;
    private final DatabaseRepository databaseRepository;

    @Value("${length.text}")
    private Integer length;

    @Override
    public void changeData() {
        DataEntity dataEntity = databaseRepository.findRandomRecordWithLocking().orElseThrow(NotFoundRandomRecordException::new);
        UUID uuid = dataEntity.getUuid();
        RLock lock = redisson.getFairLock(uuid.toString());
        boolean res;
        try {
            res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (res) {
            try {
                DataEntity reloadedDataEntity = databaseRepository.findById(uuid).orElseThrow(NotFoundRandomRecordException::new);
                updateDataEntity(reloadedDataEntity, length);
                databaseRepository.saveAndFlush(reloadedDataEntity);
                log.info("[Cache] Changed entity with uuid: {}", reloadedDataEntity.getUuid());
            } finally {
                lock.unlock();
            }
        }
    }
}
