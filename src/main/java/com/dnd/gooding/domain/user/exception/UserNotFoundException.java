package com.dnd.gooding.domain.user.exception;

import com.dnd.gooding.global.common.exception.ErrorCode;
import com.dnd.gooding.global.common.exception.ServiceException;

public class UserNotFoundException extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.USER_NOT_FOUND;
	private static final String MESSAGE_KEY = "exception.user.notfound";

	public UserNotFoundException(Object id) {
		super(ERROR_CODE, MESSAGE_KEY, new Object[] {id});
	}

}
