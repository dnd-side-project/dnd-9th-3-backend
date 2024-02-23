package com.dnd.gooding.integration.user.query;

import com.dnd.gooding.integration.IntegrationTest;
import com.dnd.gooding.user.command.application.NoMemberException;
import com.dnd.gooding.user.query.application.MemberQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("멤버 조회 통합 테스트")
public class MemberQueryServiceIntegrationTest extends IntegrationTest {

    @Autowired private MemberQueryService memberQueryService;

    @Transactional(readOnly = true)
    @DisplayName("멤버를 조회한다.")
    @Test
    void getMember() {

        // then
        Assertions.assertThrows(
                NoMemberException.class, () -> memberQueryService.getMember("youg1322@naver.com"));
    }
}
