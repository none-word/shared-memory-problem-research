package com.example.sharedmemoryproblemresearch.service;

import com.example.sharedmemoryproblemresearch.model.DataEntity;

import java.util.UUID;

public interface DatabaseService {
    DataEntity getData(UUID uuid);
    void changeData();
}
