package com.dnd.gooding.user.query.application;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dnd.gooding.user.fixture.MemberDataFixture;
import com.dnd.gooding.user.query.dao.MemberDataDao;
import com.dnd.gooding.user.query.dto.MemberData;

public class MemberQueryServiceTest {

	private MemberDataDao memberDataDao;
	private MemberQueryService memberQueryService;

	@BeforeEach
	public void beforeEach() {
		memberDataDao = Mockito.mock(MemberDataDao.class);
		memberQueryService = new MemberQueryService(memberDataDao);
	}

	@DisplayName("멤버 ID로 멤버 정보를 가져온다.")
	@Test
	public void getMember() {
		// given
		String id = "youg1322@naver.com";

		MemberData expectMemberData = MemberDataFixture.getMember();

		Mockito.when(memberDataDao.findById(id)).thenReturn(
			Optional.of(expectMemberData)
		);

		// when
		MemberData memberData = memberQueryService.getMember(id);

		// then
		Assertions.assertEquals(expectMemberData.getId(), memberData.getId());
		Assertions.assertEquals(expectMemberData.getName(), memberData.getName());
	}
}
