package com.dnd.gooding.global.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@GetMapping("/health-check")
	public String healthCheck() {
		return "health-check";
	}
}
