package com.dnd.gooding.unit.record.query.application;

import com.dnd.gooding.record.command.domain.RecordNo;
import com.dnd.gooding.record.query.application.RecordQueryService;
import com.dnd.gooding.record.query.dao.RecordDataDao;
import com.dnd.gooding.record.query.dto.RecordData;
import com.dnd.gooding.unit.record.fixture.RecordDataFixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("기록 조회 유닛 테스트")
public class RecordQueryServiceTest {

    private RecordDataDao recordDataDao;
    private RecordQueryService recordQueryService;

    @BeforeEach
    void beforeEach() {
        recordDataDao = Mockito.mock(RecordDataDao.class);
        recordQueryService = new RecordQueryService(recordDataDao);
    }

    @DisplayName("기록 ID로 기록 정보를 가져온다.")
    @Test
    void getRecord() {
        // given
        RecordNo recordNo = RecordNo.of("123qwaf124515");

        RecordData expectRecordData = RecordDataFixture.getRecord();

        Mockito.when(recordDataDao.findById(recordNo.getNumber()))
                .thenReturn(Optional.of(expectRecordData));

        // when
        RecordData recordData = recordQueryService.getRecord(recordNo);

        // then
        Assertions.assertEquals(expectRecordData.getRecordNumber(), recordData.getRecordNumber());
        Assertions.assertEquals(expectRecordData.getRecorderId(), expectRecordData.getRecorderId());
        Assertions.assertEquals(
                expectRecordData.getImages().size(), expectRecordData.getImages().size());
    }
}
