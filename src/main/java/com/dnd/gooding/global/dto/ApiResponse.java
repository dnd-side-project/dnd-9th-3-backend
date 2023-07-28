package com.dnd.gooding.global.dto;

public record ApiResponse<T>(
	T data
) {
}
