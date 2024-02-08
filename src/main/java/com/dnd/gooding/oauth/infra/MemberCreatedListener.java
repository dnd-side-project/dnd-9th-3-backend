package com.dnd.gooding.oauth.infra;

import com.dnd.gooding.user.command.application.CreateMemberService;
import com.dnd.gooding.user.command.domain.MemberCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MemberCreatedListener {

    private final CreateMemberService createMemberService;

    public MemberCreatedListener(CreateMemberService createMemberService) {
        this.createMemberService = createMemberService;
    }

    @TransactionalEventListener(
            classes = MemberCreatedEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void create(MemberCreatedEvent event) {
        createMemberService.create(event.getEmail(), event.getoAuthId().getId());
    }
}
