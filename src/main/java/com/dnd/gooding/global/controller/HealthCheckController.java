package com.dnd.gooding.global.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "헬스체크", description = "was가 작동하고 있는지 확인합니다.")
@RestController
public class HealthCheckController {

	@GetMapping("/health-check")
	public String healthCheck() {
		return "health_check";
	}
}
