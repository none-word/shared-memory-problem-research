package com.example.sharedmemoryproblemresearch.generator;

import com.example.sharedmemoryproblemresearch.model.DataEntity;
import com.example.sharedmemoryproblemresearch.repository.DatabaseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class DataGenerator {
    @Value("${length.text}")
    private Integer length;

    private final Random random;
    private final DatabaseRepository databaseRepository;

    public void generateData(Integer number) {
        List<DataEntity> dataEntityList = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            dataEntityList.add(createDataEntity());
        }
        databaseRepository.saveAllAndFlush(dataEntityList);
    }

    private DataEntity createDataEntity() {
        String text = RandomStringUtils.randomAlphabetic(length);
        Integer number = random.nextInt();
        return DataEntity.builder()
                .plainText(text)
                .number(number)
                .build();
    }
}
