package com.dnd.gooding.integration.user.command;

import com.dnd.gooding.integration.IntegrationTest;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.query.application.MemberQueryService;
import com.dnd.gooding.user.query.dto.MemberData;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateMemberServiceIntegrationTest extends IntegrationTest {

  @Autowired private CreateMemberService createMemberService;
  @Autowired private MemberQueryService memberQueryService;
  @Autowired private EntityManager entityManager;

  @DisplayName("멤버를 생성한다.")
  @Test
  void createMember() {
    // given
    String id = "youg1322@naver.com";
    String oAuthId = "12356";

    // when
    createMemberService.createMember(id, oAuthId);
    entityManager.flush();
    entityManager.clear();

    // then
    MemberData memberData = memberQueryService.getMember(id);

    Assertions.assertNotNull(memberData);
    Assertions.assertEquals(id, memberData.getId());
    Assertions.assertEquals(oAuthId, memberData.getoAuthId());
  }
}
