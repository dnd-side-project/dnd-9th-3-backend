package com.dnd.gooding.unit.fixture;

import com.dnd.gooding.record.query.dto.RecordData;

public class RecordDataFixture {

    public static RecordData getRecord() {
        return RecordData.builder()
                .recordNumber("123qwaf124515")
                .placeTitle("바다")
                .placeLatitude(123.1616161)
                .placeLongitude(151515.61616)
                .recorderId("youg1322@naver.com")
                .recorderName("haeyong")
                .title("기록1")
                .description("기록1 설명")
                .build();
    }
}
