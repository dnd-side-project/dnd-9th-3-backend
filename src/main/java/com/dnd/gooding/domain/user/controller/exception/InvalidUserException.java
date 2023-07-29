package com.dnd.gooding.domain.user.controller.exception;

import com.dnd.gooding.global.exception.ErrorCode;
import com.dnd.gooding.global.exception.ServiceException;

public class InvalidUserException extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.USER_INVALID;
	private static final String MESSAGE_KEY = "exception.user.invalid";

	public InvalidUserException(Long userId) {
		super(ERROR_CODE, MESSAGE_KEY, new Object[] {userId});
	}

}
