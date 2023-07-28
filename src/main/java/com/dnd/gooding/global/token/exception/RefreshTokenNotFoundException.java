package com.dnd.gooding.global.token.exception;

import com.dnd.gooding.global.exception.ErrorCode;
import com.dnd.gooding.global.exception.ServiceException;

public class RefreshTokenNotFoundException extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.NOT_FOUND_REFRESH_TOKEN;
	private static final String MESSAGE_KEY = "exception.user.notfound";

	public RefreshTokenNotFoundException() {
		super(ERROR_CODE, MESSAGE_KEY, new String[] {});
	}

}
