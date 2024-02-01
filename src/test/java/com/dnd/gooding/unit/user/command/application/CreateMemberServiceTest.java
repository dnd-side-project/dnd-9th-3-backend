package com.dnd.gooding.unit.user.command.application;

import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateMemberServiceTest {

  private MemberRepository memberRepository;
  private CreateMemberService createMemberService;

  @BeforeEach
  void beforeEach() {
    memberRepository = Mockito.mock(MemberRepository.class);
    createMemberService = new CreateMemberService(memberRepository);
  }

  @DisplayName("멤버를 생성한다.")
  @Test
  void createMember() {
    // given
    String id = "youg1322@naver.com";
    String oAuthId = "12356";

    // when
    createMemberService.createMember(id, oAuthId);

    // then
    Mockito.verify(memberRepository, Mockito.times(1)).findById(Mockito.any());
    Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any());
  }
}
