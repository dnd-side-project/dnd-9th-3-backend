package com.dnd.gooding.integration.user.command;

import com.dnd.gooding.integration.IntegrationTest;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.application.NoMemberException;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;
import com.dnd.gooding.user.command.domain.MemberRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("멤버 생성 통합 테스트")
class CreateMemberServiceIntegrationTest extends IntegrationTest {

    @Autowired private CreateMemberService createMemberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager entityManager;

    @DisplayName("멤버를 생성한다.")
    @Test
    void create() {
        // given
        MemberId memberId = MemberId.of("youg1322@naver.com");
        OAuthId oAuthId = OAuthId.of("12356");

        // when
        createMemberService.create(memberId.getId(), oAuthId.getId());
        entityManager.flush();
        entityManager.clear();

        // then
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);

        Assertions.assertEquals(1, 1);
        Assertions.assertNotNull(member);
        Assertions.assertEquals(memberId, member.getId());
        Assertions.assertEquals(oAuthId, member.getoAuthId());
    }
}
