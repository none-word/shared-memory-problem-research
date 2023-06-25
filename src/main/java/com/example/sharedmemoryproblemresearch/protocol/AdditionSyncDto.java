package com.example.sharedmemoryproblemresearch.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionSyncDto {
    private UUID uuid;
    private String address;
}
