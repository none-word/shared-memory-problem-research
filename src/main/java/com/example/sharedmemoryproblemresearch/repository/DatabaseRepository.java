package com.example.sharedmemoryproblemresearch.repository;

import com.example.sharedmemoryproblemresearch.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DatabaseRepository extends JpaRepository<DataEntity, UUID> {
}
