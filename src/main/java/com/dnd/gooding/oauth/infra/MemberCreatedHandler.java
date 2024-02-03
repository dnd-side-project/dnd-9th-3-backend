package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.common.event.EventHandler;
import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.domain.MemberCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class MemberCreatedHandler implements EventHandler<MemberCreatedEvent> {

  private CreateMemberService createMemberService;

  public MemberCreatedHandler(CreateMemberService createMemberService) {
    this.createMemberService = createMemberService;
  }

  @Override
  public void handle(MemberCreatedEvent event) {
    createMemberService.create(event.getEmail(), event.getoAuthId().getId());
  }
}
