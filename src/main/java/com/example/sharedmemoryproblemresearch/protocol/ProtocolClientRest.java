package com.example.sharedmemoryproblemresearch.protocol;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProtocolClientRest implements ProtocolClient {
    private final RestTemplate restTemplate;
    private final ProtocolProperties protocolProperties;

    @SneakyThrows(InterruptedException.class)
    @Override
    public void lock(UUID uuid) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://" + protocolProperties.getAddress())
                .port(protocolProperties.getPort())
                .path("/sync/add");
        boolean result;
        result = isResult(builder, uuid);
        while (!result) {
            Thread.sleep(10);
            result = isResult(builder, uuid);
        }
    }

    private boolean isResult(UriComponentsBuilder builder, UUID uuid) {
        boolean result;
        if (!Objects.equals(protocolProperties.getType(), "MASTER")) {
            result = restTemplate.postForObject(builder.toUriString(), new AdditionSyncDto(uuid, protocolProperties.getType()), ResultResponseDto.class).getResult();
        } else {
            result = ProtocolService.addNewRecord(protocolProperties.getType(), uuid);
        }
        return result;
    }

    @Override
    public void unlock(UUID uuid) {
        if (!Objects.equals(protocolProperties.getType(), "MASTER")) {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl("http://" + protocolProperties.getAddress())
                    .port(protocolProperties.getPort())
                    .path("/sync/remove");
            restTemplate.postForObject(builder.toUriString(), new DeletionSyncDto(uuid), Boolean.class);
        } else {
            ProtocolService.deleteRecord(uuid);
        }
    }
}
