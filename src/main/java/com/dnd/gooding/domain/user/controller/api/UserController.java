package com.dnd.gooding.domain.user.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.gooding.domain.user.controller.response.UserProfileResponse;
import com.dnd.gooding.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "사용자를 검색한다.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserProfileResponse> getById(@PathVariable Long id) {
		return ResponseEntity
			.ok()
			.body((userService.getById(id)));
	}
}
