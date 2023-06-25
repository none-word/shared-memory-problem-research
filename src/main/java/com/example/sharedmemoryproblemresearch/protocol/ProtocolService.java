package com.example.sharedmemoryproblemresearch.protocol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProtocolService {
    private static final ConcurrentHashMap<UUID, String> synchronizationStorage = new ConcurrentHashMap<>();

    public static synchronized boolean addNewRecord(String address, UUID uuid) {
        log.info("Addition: {}, storage: {}", uuid,  synchronizationStorage.toString());
        if (synchronizationStorage.get(uuid) == null) {
            synchronizationStorage.put(uuid, address);
            log.info("Result of addition: {}", synchronizationStorage.toString());
            return true;
        } else {
            log.info("Addition: false");
            return false;
        }
    }

    public static synchronized void deleteRecord(UUID uuid) {
        log.info("Deletion: {}, storage: {}", uuid, synchronizationStorage.toString());
        synchronizationStorage.remove(uuid);
        log.info("Result of deletion: {}", synchronizationStorage.toString());
    }
}
