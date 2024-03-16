package com.dnd.gooding.record.infra;

import com.dnd.gooding.record.command.application.out.RecordMemberPort;
import com.dnd.gooding.record.command.domain.Recorder;
import com.dnd.gooding.user.command.domain.MemberId;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.query.dto.MemberData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordMemberAdapter implements RecordMemberPort {

    private final MemberQueryService memberQueryService;

    public RecordMemberAdapter(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Transactional(readOnly = true)
    @Override
    public Recorder createRecorder(MemberId recorderMemberId) {
        MemberData memberData = memberQueryService.getMember(recorderMemberId.getId());
        return new Recorder(MemberId.of(memberData.getId()), memberData.getName());
    }
}
