package com.dnd.gooding.integration.user.command;

import com.dnd.gooding.integration.IntegrationTest;
import com.dnd.gooding.oauth.command.domain.OAuthId;
import com.dnd.gooding.user.command.domain.service.MemberService;
import com.dnd.gooding.user.exception.NoMemberException;
import com.dnd.gooding.user.command.domain.Member;
import com.dnd.gooding.user.command.domain.MemberId;
import com.dnd.gooding.user.command.domain.repository.MemberRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("멤버 생성 통합 테스트")
class MemberServiceIntegrationTest extends IntegrationTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager entityManager;

    @DisplayName("멤버를 생성한다.")
    @Test
    void create() {
        // given
        MemberId memberId = MemberId.of("youg1322@naver.com");
        OAuthId oAuthId = OAuthId.of("12356");

        // when
        memberService.create(memberId.getId(), oAuthId.getId());
        entityManager.flush();
        entityManager.clear();

        // then
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);

        Assertions.assertNotNull(member);
        Assertions.assertEquals(memberId, member.getId());
        Assertions.assertEquals(oAuthId, member.getoAuthId());
    }
}
