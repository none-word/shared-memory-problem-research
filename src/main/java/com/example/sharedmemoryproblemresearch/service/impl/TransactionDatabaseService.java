package com.example.sharedmemoryproblemresearch.service.impl;

import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionDatabaseService implements DatabaseService {
    @Override
    public DataEntity getData(UUID uuid) {
        return null;
    }

    @Override
    public void changeData() {

    }
}
