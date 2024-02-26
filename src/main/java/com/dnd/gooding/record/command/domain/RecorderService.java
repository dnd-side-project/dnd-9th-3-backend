package com.dnd.gooding.record.command.domain;

import com.dnd.gooding.user.command.domain.MemberId;

public interface RecorderService {
    Recorder createRecorder(MemberId recorderMemberId);
}
