package com.dnd.gooding.domain.onboard.controller.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OnboardRequest {
	@NotNull
	private String nickName;
	@NotNull
	private List<String> interestCodes;
}
