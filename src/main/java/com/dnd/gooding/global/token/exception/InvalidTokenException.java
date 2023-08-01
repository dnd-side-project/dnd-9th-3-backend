package com.dnd.gooding.global.token.exception;

import com.dnd.gooding.global.common.exception.ErrorCode;

public class InvalidTokenException extends TokenException {

	private static final ErrorCode ERROR_CODE = ErrorCode.INVALID_TOKEN;
	private static final String MESSAGE_KEY = "exception.token.invalid";

	public InvalidTokenException() {
		super(ERROR_CODE, MESSAGE_KEY);
	}

}
