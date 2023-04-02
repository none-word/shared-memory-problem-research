package com.example.sharedmemoryproblemresearch.service.impl;

import com.example.sharedmemoryproblemresearch.event.SimulationEvent;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import com.example.sharedmemoryproblemresearch.service.SimulationI;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SimulationService implements SimulationI, ApplicationListener<SimulationEvent> {
    private final DatabaseService databaseService;
    private final Timer timer;

    @Value("${sleep.time}")
    private Integer sleepTime;

    @Override
    public void simulate() throws InterruptedException {
        log.info("Started process of generating requests");
        while (true) {
            Timer.Sample sample = Timer.start();
            try {
                databaseService.changeData();
            } catch (OptimisticLockingFailureException e) {
                e.printStackTrace();
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
            sample.stop(timer);
            Thread.sleep(sleepTime);
        }
    }

    @Override
    public void onApplicationEvent(SimulationEvent event) {
        log.info("Catch SimulationEvent");
        try {
            simulate();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
