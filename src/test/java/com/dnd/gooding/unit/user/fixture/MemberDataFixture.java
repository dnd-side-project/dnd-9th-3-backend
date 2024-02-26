package com.dnd.gooding.unit.user.fixture;

import com.dnd.gooding.user.query.dto.MemberData;

public class MemberDataFixture {

    public static MemberData getMember() {
        return MemberData.builder().id("youg1322@naver.com").name("haeyong").build();
    }
}
