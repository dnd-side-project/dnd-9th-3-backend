package com.dnd.gooding.unit.fixture;

import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.common.model.RecordState;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import com.dnd.gooding.user.command.domain.MemberId;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

public class RecordRequestFixture {

    public static RecordRequest getRecordRequest() throws IOException {
        return RecordRequest.builder()
                .recorderMemberId(MemberId.of("youg1322@naver.com"))
                .title("ocean view")
                .description("good")
                .recordDate(LocalDateTime.now())
                .placeTitle("seoul")
                .placeLatitude(1123.567)
                .placeLongitude(1516771.23)
                .state(RecordState.PRIVATE)
                .recordScore(20)
                .interests(Set.of(new Interest("1", "쇼핑"), new Interest("2", "여행")))
                .files(RecordFileFixture.getFiles())
                .build();
    }
}
