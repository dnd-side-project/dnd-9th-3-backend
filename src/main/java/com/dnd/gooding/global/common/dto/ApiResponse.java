package com.dnd.gooding.global.common.dto;

public record ApiResponse<T>(
	T data
) {
}
