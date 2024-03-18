package com.dnd.gooding.unit.user.command;

import com.dnd.gooding.user.command.domain.repository.MemberRepository;
import com.dnd.gooding.user.command.domain.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("멤버 생성 유닛 테스트")
class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void beforeEach() {
        memberRepository = Mockito.mock(MemberRepository.class);
        memberService = new MemberService(memberRepository);
    }

    @DisplayName("멤버를 생성한다.")
    @Test
    void createMember() {
        // given
        String id = "youg1322@naver.com";
        String oAuthId = "12356";

        // when
        memberService.create(id, oAuthId);

        // then
        Mockito.verify(memberRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any());
    }
}
