package com.example.sharedmemoryproblemresearch.protocol;

import java.util.UUID;

public interface ProtocolClient {
    void lock(UUID uuid);
    void unlock(UUID uuid);
}
