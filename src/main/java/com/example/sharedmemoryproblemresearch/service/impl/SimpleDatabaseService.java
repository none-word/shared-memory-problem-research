package com.example.sharedmemoryproblemresearch.service.impl;

import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SimpleDatabaseService implements DatabaseService {
    private final DatabaseRepository databaseRepository;

    @Override
    public DataEntity getData(UUID uuid) {
        return null;
    }

    @Override
    public void changeData() {
//        DataEntity dataEntity = databaseRepository.;
    }
}
