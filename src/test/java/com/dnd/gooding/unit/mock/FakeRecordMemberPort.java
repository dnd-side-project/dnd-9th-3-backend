package com.dnd.gooding.unit.mock;

import com.dnd.gooding.record.command.application.out.RecordMemberPort;
import com.dnd.gooding.record.command.domain.Recorder;
import com.dnd.gooding.user.command.domain.MemberId;

public class FakeRecordMemberPort implements RecordMemberPort {

    @Override
    public Recorder createRecorder(MemberId recorderMemberId) {
        return Recorder.builder()
                .memberId(MemberId.of("youg1322@naver.com"))
                .memberName("haeyong")
                .build();
    }
}
