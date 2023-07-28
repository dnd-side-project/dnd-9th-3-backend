package com.dnd.gooding.global.dto;

import com.dnd.gooding.global.exception.ErrorCode;

public record ErrorResponse(
	String code,
	String message
) {
	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code.getCode(), code.getMessage());
	}
}
