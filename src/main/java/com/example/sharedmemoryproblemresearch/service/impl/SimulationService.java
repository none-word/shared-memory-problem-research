package com.example.sharedmemoryproblemresearch.service.impl;

import com.example.sharedmemoryproblemresearch.event.SimulationEvent;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import com.example.sharedmemoryproblemresearch.service.SimulationI;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SimulationService implements SimulationI, ApplicationListener<SimulationEvent> {
    private final DatabaseService databaseService;
    private final Timer timer;

    @Value("${sleep.time}")
    private Integer sleepTime;
    @Value("${simulation.threads.number}")
    private Integer threadsNumber;

    @SneakyThrows
    @Override
    public void simulate() {
        log.info("Started process of generating requests");
        while (true) {
            Timer.Sample sample = Timer.start();
            try {
                databaseService.changeData();
            } catch (OptimisticLockingFailureException e) {
                log.error("ERROR: {}", e.getMessage());
                e.printStackTrace();
            } catch (DataAccessException e) {
                log.error("ERROR: {}", e.getMessage());
                e.printStackTrace();
            }
            sample.stop(timer);
            Thread.sleep(sleepTime);
        }
    }

    @Override
    public void onApplicationEvent(SimulationEvent event) {
        log.info("Catch SimulationEvent");
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
        for (int i = 0; i < threadsNumber; i++) {
            executorService.submit(this::simulate);
        }
        executorService.shutdown();
    }
}
