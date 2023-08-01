package com.dnd.gooding.global.token.exception;

import com.dnd.gooding.global.common.exception.ErrorCode;
import com.dnd.gooding.global.common.exception.ServiceException;

public abstract class TokenException extends ServiceException {

	protected TokenException(ErrorCode errorCode, String messageKey) {
		super(errorCode, messageKey, null);
	}
}
