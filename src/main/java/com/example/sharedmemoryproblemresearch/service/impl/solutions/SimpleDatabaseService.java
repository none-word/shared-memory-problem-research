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

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.updateDataEntity;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "solution.type", havingValue = "SIMPLE")
@Service
public class SimpleDatabaseService implements DatabaseService {
    private final DatabaseRepository databaseRepository;
    @Value("${length.text}")
    private Integer length;
    @Override
    public void changeData() {
        DataEntity dataEntity = databaseRepository.findRandomRecord().orElseThrow(NotFoundRandomRecordException::new);
        updateDataEntity(dataEntity, length);
        databaseRepository.saveAndFlush(dataEntity);
        log.info("[Simple] Changed entity with uuid: {}", dataEntity.getUuid());
    }
}
