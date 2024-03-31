package com.dnd.gooding.mail.infra;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dnd.gooding.mail.application.EmailSender;
import com.dnd.gooding.oauth.command.domain.event.LoginSuccessEvent;

@Component
public class EmailSendEventHandler {

	private final EmailSender emailSender;

	public EmailSendEventHandler(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Async
	@EventListener
	public void sendLoginSuccessMail(LoginSuccessEvent event) throws Exception {
		emailSender.sendLoginSuccess(event.getEmail());
	}
}
