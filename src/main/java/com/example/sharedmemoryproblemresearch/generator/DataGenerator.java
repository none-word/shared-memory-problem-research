package com.example.sharedmemoryproblemresearch.generator;

import com.example.sharedmemoryproblemresearch.event.SimulationEvent;
import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.LinkedList;
import java.util.List;

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.createDataEntity;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataGenerator {
    @Value("${length.text}")
    private Integer length;

    @Value("${records.number}")
    private Integer number;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final DatabaseRepository databaseRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void generateData() {
        log.info("Start initialization");
        List<DataEntity> dataEntityList = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            dataEntityList.add(createDataEntity(length));
        }
        databaseRepository.saveAllAndFlush(dataEntityList);
        log.info("End initialization and publish event");
        applicationEventPublisher.publishEvent(new SimulationEvent(this));
    }

    @PreDestroy
    public void deleteData() {
        log.info("Start deletion");
        databaseRepository.deleteAll();
        log.info("End deletion");
    }
}
