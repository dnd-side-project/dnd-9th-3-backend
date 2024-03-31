package com.dnd.gooding.unit.fixture;

import com.dnd.gooding.user.ui.dto.request.MemberRequest;

public class MemberRequestFixture {

    public static MemberRequest create() {
        return MemberRequest.builder()
                .id("youg1322@naver.com")
                .name("haeyong")
                .emails(null)
                .interests(null)
                .build();
    }
}
