package com.dnd.gooding.eventstore.api;

public class PayloadConvertException extends RuntimeException {

	public PayloadConvertException(Exception e) {
		super(e);
	}
}
