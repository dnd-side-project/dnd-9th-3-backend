package com.dnd.gooding.global.s3.exception;

import com.dnd.gooding.global.common.exception.ErrorCode;
import com.dnd.gooding.global.common.exception.ServiceException;

public class IllegalArgumentS3Exception extends ServiceException {

	private static final ErrorCode ERROR_CODE = ErrorCode.FILE_CONVERT_FAIL;
	private static final String MESSAGE_KEY = "exception.s3.illegal";

	public IllegalArgumentS3Exception(String fileName) {
		super(ERROR_CODE, MESSAGE_KEY, new Object[] {fileName});
	}
}
