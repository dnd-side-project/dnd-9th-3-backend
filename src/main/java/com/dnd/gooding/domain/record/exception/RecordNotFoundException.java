package com.dnd.gooding.domain.record.exception;

import com.dnd.gooding.global.common.exception.ErrorCode;
import com.dnd.gooding.global.common.exception.ServiceException;

public class RecordNotFoundException extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.RECORD_NOT_FOUND;
	private static final String MESSAGE_KEY = "exception.record.notfound";

	public RecordNotFoundException(Object id) {
		super(ERROR_CODE, MESSAGE_KEY, new Object[] {id});
	}
}
