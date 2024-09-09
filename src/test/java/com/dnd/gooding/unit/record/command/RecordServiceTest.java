package com.dnd.gooding.unit.record.command;

import com.dnd.gooding.record.command.application.out.RecordFilePort;
import com.dnd.gooding.record.command.application.out.RecordReplacePort;
import com.dnd.gooding.record.command.domain.Record;
import com.dnd.gooding.record.command.domain.service.RecordService;
import com.dnd.gooding.record.ui.dto.request.RecordRequest;
import com.dnd.gooding.unit.fixture.RecordRequestFixture;
import com.dnd.gooding.unit.mock.FakeRecordMemberPort;
import com.dnd.gooding.unit.mock.FakeRecordRepository;
import com.dnd.gooding.user.command.domain.MemberId;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("기록 생성 유닛 테스트")
class RecordServiceTest {

    private FakeRecordRepository fakeRecordRepository;
    private FakeRecordMemberPort fakeRecordMemberPort;
    private RecordFilePort recordFilePort;
    private RecordService recordService;
    private RecordReplacePort recordReplacePort;

    @BeforeEach
    void beforeEach() {
        fakeRecordRepository = Mockito.spy(FakeRecordRepository.class);
        fakeRecordMemberPort = Mockito.spy(FakeRecordMemberPort.class);
        recordFilePort = Mockito.mock(RecordFilePort.class);
        recordReplacePort = Mockito.mock(RecordReplacePort.class);
        recordService =
                new RecordService(
                        fakeRecordRepository, fakeRecordMemberPort, recordFilePort, recordReplacePort);
    }

    @DisplayName("기록을 생성한다.")
    @Test
    void createRecord() throws IOException {
        // given
        RecordRequest expectRecordRequest = RecordRequestFixture.getRecordRequest();

        // when
        Record record = recordService.create(expectRecordRequest);

        // then
        Mockito.verify(fakeRecordRepository, Mockito.times(1)).nextRecordNo();
        Mockito.verify(fakeRecordMemberPort, Mockito.times(1))
                .createRecorder(MemberId.of("youg1322@naver.com"));

        Assertions.assertEquals(
                expectRecordRequest.getRecorderMemberId(), record.getRecorder().getMemberId());
        Assertions.assertEquals(expectRecordRequest.getFiles().size(), record.getImages().size());
    }
}
