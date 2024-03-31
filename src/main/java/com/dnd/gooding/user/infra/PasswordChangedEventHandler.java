package com.dnd.gooding.user.infra;

import com.dnd.gooding.user.command.domain.event.PasswordChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PasswordChangedEventHandler {

    @EventListener(PasswordChangedEvent.class)
    public void handle(PasswordChangedEvent event) {
        // 이메일 발송 코드
    }
}
