package com.dnd.gooding.record.command.application.out;

import com.dnd.gooding.record.command.domain.Recorder;
import com.dnd.gooding.user.command.domain.MemberId;

public interface RecordMemberPort {

    Recorder createRecorder(MemberId recorderMemberId);
}
