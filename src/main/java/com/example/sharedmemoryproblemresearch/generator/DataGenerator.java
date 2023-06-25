package com.example.sharedmemoryproblemresearch.generator;

import com.example.sharedmemoryproblemresearch.event.SimulationEvent;
import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.sharedmemoryproblemresearch.utils.DataUtils.createDataEntity;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataGenerator {
    @Value("${length.text}")
    private Integer length;

    @Value("${records.number}")
    private Integer number;
    @Value("${database.initialization.threads.number}")
    private Integer threadsNumber;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final DatabaseRepository databaseRepository;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void fillDatabase() {
        log.info("Start initialization");
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            tasks.add(() -> { generateData(number/threadsNumber); return null; });
        }
        executorService.invokeAll(tasks);
        executorService.shutdown();
        log.info("End initialization and publish event");
        applicationEventPublisher.publishEvent(new SimulationEvent(this));
    }

    public void generateData(Integer numberOfRecords) {
        List<DataEntity> dataEntityList = new LinkedList<>();
        for (int i = 0; i < numberOfRecords; i++) {
            if (i % 1000 == 0) {
                databaseRepository.saveAllAndFlush(dataEntityList);
                dataEntityList = new LinkedList<>();
                log.info("{} records saved", i);
            }
            dataEntityList.add(createDataEntity(length));
        }
        databaseRepository.saveAllAndFlush(dataEntityList);
    }

    @PreDestroy
    public void deleteData() {
        log.info("Start deletion");
        databaseRepository.deleteAll();
        log.info("End deletion");
    }
}
