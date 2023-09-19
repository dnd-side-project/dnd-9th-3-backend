package com.dnd.gooding.global.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthCheckController {

	@GetMapping("/health-check")
	public String healthCheck() {
		log.info("[HealthCheckController] health-check");
		return "health-check";
	}
}
