package com.dnd.gooding.global.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/health-check")
	public String healthCheck() {
		logger.info("[HealthCheckController] health-check");
		return "health-check";
	}
}
