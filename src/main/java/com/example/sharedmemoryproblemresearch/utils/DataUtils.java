package com.example.sharedmemoryproblemresearch.utils;

import com.example.sharedmemoryproblemresearch.model.DataEntity;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

@UtilityClass
public class DataUtils {
     public static Random random;

     static {
         random = new Random();
     }

    public static DataEntity createDataEntity(Integer length) {
        String text = RandomStringUtils.randomAlphabetic(length);
        Integer number = random.nextInt();
        return DataEntity.builder()
                .plainText(text)
                .number(number)
                .build();
    }

    public static void updateDataEntity(DataEntity dataEntity, Integer length) {
        String text = RandomStringUtils.randomAlphabetic(length);
        Integer number = random.nextInt();
        dataEntity.setPlainText(text);
        dataEntity.setNumber(number);
    }
}
