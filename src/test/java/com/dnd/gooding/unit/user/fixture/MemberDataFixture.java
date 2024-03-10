package com.dnd.gooding.unit.user.fixture;

import com.dnd.gooding.common.model.Email;
import com.dnd.gooding.common.model.Interest;
import com.dnd.gooding.user.query.dto.MemberData;
import java.util.List;

public class MemberDataFixture {

    public static MemberData getMember() {
        return MemberData.builder()
                .id("youg1322@naver.com")
                .name("haeyong")
                .emails(List.of(new Email("yong80211@gmail.com")))
                .interests(List.of(new Interest("1", "쇼핑"), new Interest("2", "여행")))
                .oAuthId("2942669123")
                .imageUrl(
                        "http://k.kakaocdn.net/dn/bwXMb4/btsffv6Enze/CWxCrOgCvTAUz5FxUjWLUk/img_640x640.jpg")
                .build();
    }
}
