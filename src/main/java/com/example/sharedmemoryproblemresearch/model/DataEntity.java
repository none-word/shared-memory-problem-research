package com.example.sharedmemoryproblemresearch.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class DataEntity {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID uuid;

    private String plainText;
    private Integer number;

    @CreationTimestamp
    private LocalDateTime creationTimeStamp;

    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;
}
