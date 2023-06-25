package com.example.sharedmemoryproblemresearch.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProtocolController {
    @PostMapping("/sync/add")
    public ResponseEntity<ResultResponseDto> syncAddition(@RequestBody AdditionSyncDto additionSyncDto) {
        boolean result = ProtocolService.addNewRecord(
                additionSyncDto.getAddress(),
                additionSyncDto.getUuid()
        );
        return ResponseEntity.ok(new ResultResponseDto(result));
    }

    @PostMapping("/sync/remove")
    public ResponseEntity<Void> syncDeletion(@RequestBody DeletionSyncDto deletionSyncDto) {
        ProtocolService.deleteRecord(deletionSyncDto.getUuid());
        return ResponseEntity.ok().build();
    }
}
