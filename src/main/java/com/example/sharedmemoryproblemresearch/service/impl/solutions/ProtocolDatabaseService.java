package com.example.sharedmemoryproblemresearch.service.impl.solutions;

import com.example.sharedmemoryproblemresearch.exception.NotFoundRandomRecordException;
import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.protocol.ProtocolClient;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.updateDataEntity;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "solution.type", havingValue = "PROTOCOL")
@Service
public class ProtocolDatabaseService implements DatabaseService {
    private final DatabaseRepository databaseRepository;
    private final ProtocolClient protocolClient;

    @Value("${length.text}")
    private Integer length;

    @Override
    public void changeData() {
        DataEntity dataEntity = databaseRepository.findRandomRecord().orElseThrow(NotFoundRandomRecordException::new);
        UUID uuid = dataEntity.getUuid();
        protocolClient.lock(uuid);
        dataEntity = databaseRepository.findById(uuid).orElseThrow(NotFoundRandomRecordException::new);
        updateDataEntity(dataEntity, length);
        databaseRepository.saveAndFlush(dataEntity);
        log.info("[Protocol] Changed entity with uuid: {}", dataEntity.getUuid());
        protocolClient.unlock(uuid);
    }
}
