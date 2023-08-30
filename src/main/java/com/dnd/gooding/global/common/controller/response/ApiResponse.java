package com.dnd.gooding.global.common.controller.response;

public record ApiResponse<T>(
	T data
) {
}
