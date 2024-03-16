package com.dnd.gooding.mail.exception;

public class EmailSendException extends RuntimeException {
	public EmailSendException(Exception e) {
		super(e);
	}
}
