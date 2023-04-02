package com.example.sharedmemoryproblemresearch.service.impl.solutions;

import com.example.sharedmemoryproblemresearch.exception.NotFoundRandomRecordException;
import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.updateDataEntity;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "solution.type", havingValue = "LOCKING")
@Service
public class LockingDatabaseService implements DatabaseService {
    private final DatabaseRepository databaseRepository;

    @Value("${length.text}")
    private Integer length;

    @Transactional
    @Override
    public void changeData() {
        DataEntity dataEntity = databaseRepository.findRandomRecordWithLocking().orElseThrow(NotFoundRandomRecordException::new);
        updateDataEntity(dataEntity, length);
        databaseRepository.saveAndFlush(dataEntity);
        log.info("[Locking] Changed entity with uuid: {}", dataEntity.getUuid());
    }
}
