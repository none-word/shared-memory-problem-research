package com.example.sharedmemoryproblemresearch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
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

    @Version
    private Integer version;
}
