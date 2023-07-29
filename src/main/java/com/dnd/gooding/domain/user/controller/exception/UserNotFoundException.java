package com.dnd.gooding.domain.user.controller.exception;

import com.dnd.gooding.global.exception.ErrorCode;
import com.dnd.gooding.global.exception.ServiceException;

public class UserNotFoundException extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.USER_NOT_FOUND;
	private static final String MESSAGE_KEY = "exception.user.notfound";

	public UserNotFoundException(Long userId) {
		super(ERROR_CODE, MESSAGE_KEY, new Object[] {userId});
	}

}
