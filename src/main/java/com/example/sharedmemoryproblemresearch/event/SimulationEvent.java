package com.example.sharedmemoryproblemresearch.event;

import org.springframework.context.ApplicationEvent;

public class SimulationEvent extends ApplicationEvent {
    public SimulationEvent(Object source) {
        super(source);
    }
}
