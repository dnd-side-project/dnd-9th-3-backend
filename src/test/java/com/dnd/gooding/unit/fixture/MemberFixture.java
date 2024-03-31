package com.dnd.gooding.unit.fixture;

import com.dnd.gooding.common.model.UserRole;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;

public class MemberFixture {

    public static Member create() {
        return Member.builder()
                .id(MemberId.of("youg1322@naver.com"))
                .name("haeyong")
                .emails(null)
                .interests(null)
                .userRole(UserRole.ROLE_USER.name())
                .oAuthId(OAuthId.of("12356"))
                .build();
    }
}
