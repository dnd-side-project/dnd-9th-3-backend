package com.dnd.gooding.user.command.application.in;

import com.dnd.gooding.user.ui.dto.request.MemberRequest;

public interface UpdateMemberUseCase {

	void updateMember(MemberRequest memberRequest);
}
