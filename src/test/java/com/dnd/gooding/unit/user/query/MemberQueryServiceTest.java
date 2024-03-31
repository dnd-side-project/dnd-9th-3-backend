package com.dnd.gooding.unit.user.query;

import com.dnd.gooding.unit.fixture.MemberDataFixture;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.query.dao.MemberDataDao;
import com.dnd.gooding.user.query.dto.MemberData;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("멤버 조회 유닛 테스트")
class MemberQueryServiceTest {

    private MemberDataDao memberDataDao;
    private MemberQueryService memberQueryService;

    @BeforeEach
    void beforeEach() {
        memberDataDao = Mockito.mock(MemberDataDao.class);
        memberQueryService = new MemberQueryService(memberDataDao);
    }

    @DisplayName("멤버 ID로 멤버 정보를 가져온다.")
    @Test
    void getMember() {
        // given
        String id = "youg1322@naver.com";

        MemberData expectMemberData = MemberDataFixture.getMember();

        Mockito.when(memberDataDao.findById(id)).thenReturn(Optional.of(expectMemberData));

        // when
        MemberData memberData = memberQueryService.getMember(id);

        // then
        Assertions.assertEquals(expectMemberData.getId(), memberData.getId());
        Assertions.assertEquals(expectMemberData.getName(), memberData.getName());
    }
}
