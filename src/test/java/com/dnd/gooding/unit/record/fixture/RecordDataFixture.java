package com.dnd.gooding.unit.record.fixture;

import com.dnd.gooding.record.command.domain.Image;
import com.dnd.gooding.record.query.dto.RecordData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecordDataFixture {

    public static RecordData getRecord() {
        List<Image> images = new ArrayList<>();
        images.add(new Image("http://qwe123123.jpg", LocalDateTime.now()));
        images.add(new Image("http://qwe12315153.jpg", LocalDateTime.now()));

        return RecordData.builder()
                .recordNumber("123qwaf124515")
                .placeTitle("바다")
                .placeLatitude(123.1616161)
                .placeLongitude(151515.61616)
                .recorderId("youg1322@naver.com")
                .recorderName("haeyong")
                .title("기록1")
                .description("기록1 설명")
                .images(images)
                .build();
    }
}
