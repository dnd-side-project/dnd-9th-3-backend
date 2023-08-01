package com.dnd.gooding.global.token.exception;

import com.dnd.gooding.global.common.exception.ErrorCode;
import com.dnd.gooding.global.common.exception.ServiceException;

public class NotFoundCookieException extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.NOT_FOUND_COOKIE;
	private static final String MESSAGE_KEY = "exception.user.notfound";

	public NotFoundCookieException() {
		super(ERROR_CODE, MESSAGE_KEY, new String[] {});
	}
}
