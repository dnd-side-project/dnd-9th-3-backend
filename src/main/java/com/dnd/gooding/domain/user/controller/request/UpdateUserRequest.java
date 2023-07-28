package com.dnd.gooding.domain.user.controller.request;

import javax.validation.constraints.NotBlank;

public record UpdateUserRequest(
	@NotBlank String nickName,
	@NotBlank String profileImgUrl
) {
}
