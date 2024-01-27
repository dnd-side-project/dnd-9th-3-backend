package com.dnd.gooding.user.command.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dnd.gooding.user.command.domain.MemberRepository;

public class CreateMemberServiceTest {

	private MemberRepository memberRepository;
	private CreateMemberService createMemberService;

	@BeforeEach
	public void beforeEach() {
		memberRepository = Mockito.mock(MemberRepository.class);
		createMemberService = new CreateMemberService(memberRepository);
	}

	@DisplayName("멤버를 생성한다.")
	@Test
	public void createMember() {
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