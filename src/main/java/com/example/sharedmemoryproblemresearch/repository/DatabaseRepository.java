package com.example.sharedmemoryproblemresearch.repository;

import com.example.sharedmemoryproblemresearch.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseRepository extends JpaRepository<DataEntity, UUID> {
    @Query(value = "SELECT * FROM data_entity ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<DataEntity> findRandomRecord();

    @Query(value = "SELECT * FROM data_entity ORDER BY RANDOM() LIMIT 1 FOR UPDATE", nativeQuery = true)
    Optional<DataEntity> findRandomRecordWithLocking();
}
